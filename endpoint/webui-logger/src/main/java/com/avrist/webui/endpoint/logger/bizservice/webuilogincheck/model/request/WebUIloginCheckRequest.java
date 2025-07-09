package com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.model.request;

import com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.model.helper.LoginCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WebUIloginCheckRequest {
    @NotEmpty(message = "username can't left blank")
    private String username;
    @NotEmpty(message = "password can't left blank")
    private String password;

    public static LoginCheck.Input toLoginCheckInput(WebUIloginCheckRequest input){
        return LoginCheck.Input.builder()
                .username(input.getUsername())
                .password(input.getPassword())
                .build();
    }
}
