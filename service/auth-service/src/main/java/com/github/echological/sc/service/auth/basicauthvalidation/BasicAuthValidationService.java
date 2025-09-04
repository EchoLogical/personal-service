package com.github.echological.sc.service.auth.basicauthvalidation;

import com.github.echological.sc.datasource.auth.repository.AuthUserRepository;
import com.github.echological.sc.global.constant.ServiceStatus;
import com.github.echological.sc.global.contract.BusinessServiceContract;
import com.github.echological.sc.global.exception.BusinessServiceValidationException;
import com.github.echological.sc.global.util.SysMessageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasicAuthValidationService implements BusinessServiceContract<String, Boolean> {

    private final AuthUserRepository authUserRepository;

    private String decodeBase64Token(String base64){
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            return new String(bytes, StandardCharsets.UTF_8);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Boolean execute(String input, String lang) throws BusinessServiceValidationException {
        if(ObjectUtils.isEmpty(input)){
            throw new BusinessServiceValidationException(
                    ServiceStatus.UNAUTHORIZED.getCode(),
                    ServiceStatus.UNAUTHORIZED.getStatus(),
                    HttpStatus.UNAUTHORIZED,
                    List.of(SysMessageUtil.getMessage(lang, "UNAUTHORIZED"))
            );
        }

        var tokenParts = input.split(" ");

        if(tokenParts.length != 2){
            throw new BusinessServiceValidationException(
                    ServiceStatus.UNAUTHORIZED.getCode(),
                    ServiceStatus.UNAUTHORIZED.getStatus(),
                    HttpStatus.UNAUTHORIZED,
                    List.of(SysMessageUtil.getMessage(lang, "UNAUTHORIZED"))
            );
        }

        if(!tokenParts[0].equalsIgnoreCase("basic")){
            return false;
        }

        var credentials = decodeBase64Token(tokenParts[1]);

        if(ObjectUtils.isEmpty(credentials)){
            throw new BusinessServiceValidationException(
                    ServiceStatus.UNAUTHORIZED.getCode(),
                    ServiceStatus.UNAUTHORIZED.getStatus(),
                    HttpStatus.UNAUTHORIZED,
                    List.of(SysMessageUtil.getMessage(lang, "UNAUTHORIZED"))
            );
        }

        var credentialsParts = credentials.split(":");

        if(credentialsParts.length != 2){
            throw new BusinessServiceValidationException(
                    ServiceStatus.UNAUTHORIZED.getCode(),
                    ServiceStatus.UNAUTHORIZED.getStatus(),
                    HttpStatus.UNAUTHORIZED,
                    List.of(SysMessageUtil.getMessage(lang, "UNAUTHORIZED"))
            );
        }

        var userData = authUserRepository.findByUsername(credentialsParts[0]);

        if(userData.isEmpty()){
            throw new BusinessServiceValidationException(
                    ServiceStatus.UNAUTHORIZED.getCode(),
                    ServiceStatus.UNAUTHORIZED.getStatus(),
                    HttpStatus.UNAUTHORIZED,
                    List.of(SysMessageUtil.getMessage(lang, "INVALID_USERNAME_OR_PASSWORD"))
            );
        }

        if(!BCrypt.checkpw(credentialsParts[1], userData.get().getPassword())){
            throw new BusinessServiceValidationException(
                    ServiceStatus.UNAUTHORIZED.getCode(),
                    ServiceStatus.UNAUTHORIZED.getStatus(),
                    HttpStatus.UNAUTHORIZED,
                    List.of(SysMessageUtil.getMessage(lang, "INVALID_USERNAME_OR_PASSWORD"))
            );
        }


        return true;
    }

}
