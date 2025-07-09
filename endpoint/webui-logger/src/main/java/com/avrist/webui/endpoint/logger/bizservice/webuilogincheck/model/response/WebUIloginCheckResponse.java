package com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.model.response;

import com.avrist.webui.endpoint.logger.bizservice.webuilogincheck.model.helper.LoginCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebUIloginCheckResponse {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String departement;
    private String jobTitle;
    private String displayName;

    public static WebUIloginCheckResponse fromLoginCheckOutput(LoginCheck.Output loginCheckOutput){
        return WebUIloginCheckResponse.builder()
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
