package com.avrist.webui.bizservice.webuilogincheck.model.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LoginCheck {

    private Input input;
    private Output output;

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Input {
        private String username;
        private String password;
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Output {
        private String userId;
        private String firstName;
        private String lastName;
        private String email;
        private String departement;
        private String jobTitle;
        private String displayName;
    }
}
