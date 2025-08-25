package com.github.echological.sc.datasource.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_user_roles")
@IdClass(AuthUserRoleId.class)
public class AuthUserRoleEntity {

    @Id
    @Column(name = "user_id", length = 100, nullable = false)
    private String userId;

    @Id
    @Column(name = "role_id", length = 100, nullable = false)
    private String roleId;
}
