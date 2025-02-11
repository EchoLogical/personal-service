package com.avrist.webui.service.logincheck;

import com.avrist.core.service.ldap.AvristLdapService;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import com.avrist.webui.service.GeneralService;
import com.avrist.webui.service.logincheck.model.input.LoginCheckInput;
import com.avrist.webui.service.logincheck.model.output.LoginCheckOutput;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginCheckService implements GeneralService<LoginCheckInput, LoginCheckOutput> {

    private final AvristLdapService ldapService;

    @Autowired
    public LoginCheckService(AvristLdapService ldapService) {
        this.ldapService = ldapService;
    }

    @Override
    public LoginCheckOutput apply(LoginCheckInput input) throws BusinessServiceValidationException {
        var credentials = ldapService.checkLDAPAuthCredentials(
          input.getUsername(), input.getPassword());
        if(ObjectUtils.isEmpty(credentials)) return null;
        return LoginCheckOutput.builder()
                .userId(credentials.getUserId())
                .firstName(credentials.getFirstName())
                .lastName(credentials.getLastName())
                .email(credentials.getEmail())
                .departement(credentials.getDepartement())
                .jobTitle(credentials.getJobTitle())
                .displayName(credentials.getDisplayName())
                .build();
    }
}
