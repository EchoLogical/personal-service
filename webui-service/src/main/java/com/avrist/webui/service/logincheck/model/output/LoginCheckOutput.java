package com.avrist.webui.service.logincheck.model.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginCheckOutput {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String departement;
    private String jobTitle;
    private String displayName;
}
