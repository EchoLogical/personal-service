package com.avrist.webui.bizservice.webuilogincheck.model.response;

import com.avrist.webui.service.logincheck.model.output.LoginCheckOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebUIloginResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String departement;
    private String jobTitle;
    private String displayName;

    public static WebUIloginResponse fromLoginCheckOutput(LoginCheckOutput loginCheckOutput){
        return WebUIloginResponse.builder()
                .userId(loginCheckOutput.getUserId())
                .firstName(loginCheckOutput.getFirstName())
                .lastName(loginCheckOutput.getLastName())
                .email(loginCheckOutput.getEmail())
                .departement(loginCheckOutput.getDepartement())
                .jobTitle(loginCheckOutput.getJobTitle())
                .displayName(loginCheckOutput.getDisplayName())
                .build();
    }

}
