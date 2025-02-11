package com.avrist.webui.bizservice.webuilogincheck.model.request;

import com.avrist.webui.service.logincheck.model.input.LoginCheckInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WebUIloginRequest {
    @NotEmpty(message = "username can't left blank")
    private String username;
    @NotEmpty(message = "password can't left blank")
    private String password;

    public static LoginCheckInput toLoginCheckInput(WebUIloginRequest input){
        return LoginCheckInput.builder()
                .username(input.getUsername())
                .password(input.getPassword())
                .build();
    }
}
