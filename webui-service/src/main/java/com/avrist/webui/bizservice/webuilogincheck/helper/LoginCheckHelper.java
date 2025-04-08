package com.avrist.webui.bizservice.webuilogincheck.helper;

import com.avrist.core.service.ldap.AvristLdapService;
import com.avrist.webui.bizservice.webuilogincheck.model.helper.LoginCheck;
import com.avrist.webui.global.exception.BusinessServiceValidationException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginCheckHelper {

    private final AvristLdapService ldapService;

    @Autowired
    public LoginCheckHelper(AvristLdapService ldapService) {
        this.ldapService = ldapService;
    }

    public LoginCheck.Output check(LoginCheck.Input input) throws BusinessServiceValidationException {
        var credentials = ldapService.checkLDAPAuthCredentials(
          input.getUsername(), input.getPassword());
        if(ObjectUtils.isEmpty(credentials)) return null;
        return LoginCheck.Output.builder()
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
