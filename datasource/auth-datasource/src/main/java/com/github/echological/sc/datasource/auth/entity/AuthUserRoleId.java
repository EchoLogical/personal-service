package com.github.echological.sc.datasource.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserRoleId implements Serializable {
    private String userId;
    private String roleId;
}
