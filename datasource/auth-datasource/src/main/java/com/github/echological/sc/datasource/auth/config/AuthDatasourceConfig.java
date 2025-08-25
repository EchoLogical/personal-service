package com.github.echological.sc.datasource.auth.config;

import com.github.echological.sc.datasource.auth.constant.AuthDSConstant;
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

/**
 * Datasource and JPA configuration dedicated to the Gateway repository module.
 *
 * <p>Provides an isolated EntityManagerFactory, DataSource (HikariCP), and TransactionManager
 * for the gateway-related entities and repositories.</p>
 */
@Configuration
@EnableJpaRepositories(
        basePackages = AuthDSConstant.REPO_PACKAGE,
        entityManagerFactoryRef = "authEntityManager",
        transactionManagerRef = "authTrxManager"
)
public class AuthDatasourceConfig {

    private final Environment env;

    @Autowired
    public AuthDatasourceConfig(Environment env) {
        this.env = env;
    }

    /**
     * Helper to read properties under the db.gateway.* namespace.
     */
    private String prop(String key){
        return env.getProperty(String.format(AuthDSConstant.PROP, key));
    }

    /**
     * Configures a dedicated EntityManagerFactory scanning only gateway entities.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean authEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(authDatasource());
        em.setPackagesToScan((AuthDSConstant.ENTITY_PACKAGE));

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

    /**
     * Configures the HikariCP DataSource for the gateway database.
     */
    @Bean
    public DataSource authDatasource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(prop("hikari.driver-class-name"));
        dataSource.setJdbcUrl(prop("hikari.url"));
        dataSource.setUsername(prop("hikari.username"));
        dataSource.setPassword(prop("hikari.password"));
        dataSource.setMaximumPoolSize(Integer.parseInt(prop("hikari.max-pool-size")));
        return dataSource;
    }

    /**
     * Transaction manager bound to the gateway EntityManagerFactory.
     */
    @Bean
    public PlatformTransactionManager authTrxManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(authEntityManager().getObject());
        return transactionManager;
    }

}
