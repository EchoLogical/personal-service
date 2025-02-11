package com.avrist.webui.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.avrist.webui.datasource.repository",
        entityManagerFactoryRef = "webuiEntityManager",
        transactionManagerRef = "webuiTrxManager"
)
public class WebUIDatasourceConfig {

    private final Environment env;

    @Autowired
    public WebUIDatasourceConfig(Environment env) {
        this.env = env;
    }

    private String prop(String key){
        return env.getProperty(String.format("db.webui.%s", key));
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean webuiEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(webuiDatasource());
        em.setPackagesToScan(("com.avrist.core.datasource.webui.entity"));

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, String> prop = new HashMap<>();
        prop.put("hibernate.hbm2ddl.auto", prop("hibernate.ddl-auto"));
        prop.put("hibernate.dialect", prop("hibernate.dialect"));
        prop.put("hibernate.show_sql", prop("hibernate.show-sql"));
        prop.put("hibenate.format_sql", prop("hibernate.format-sql"));
        em.setJpaPropertyMap(prop);
        return em;
    }

    @Bean
    public DataSource webuiDatasource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(prop("hikari.driver-class-name"));
        dataSource.setJdbcUrl(prop("hikari.url"));
        dataSource.setUsername(prop("hikari.username"));
        dataSource.setPassword(prop("hikari.password"));
        dataSource.setMaximumPoolSize(Integer.parseInt(prop("hikari.max-pool-size")));
        dataSource.addDataSourceProperty("encrypt", false);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager webuiTrxManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(webuiEntityManager().getObject());
        return transactionManager;
    }

}
