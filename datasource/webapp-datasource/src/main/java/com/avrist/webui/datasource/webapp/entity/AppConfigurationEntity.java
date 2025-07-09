package com.avrist.webui.datasource.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AppConfiguration")
public class AppConfigurationEntity implements Serializable {

    private static final long serialVersionUID = 8252670510726871312L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private UUID id;
    @Column(name = "ConfigName", nullable = false)
    private String configName;
    @Column(name = "ConfigDesc", nullable = false)
    private String configDesc;
    @Column(name = "ConfigValue", nullable = false)
    private String configValue;

}
