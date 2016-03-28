package com.tilepay.core.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.dialect.H2Dialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableJpaRepositories("com.tilepay.core")
@EnableTransactionManagement
public class JpaConfiguration {

    public static final String PACKAGES_TO_SCAN = "com.tilepay.core.model";

    @Inject
    private DataSource dataSource;

    @Inject
    private NetworkParams networkParams;

    @Bean
    public Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.dialect", H2Dialect.class.getName());
        props.put("javax.persistence.validation.factory", validator());
        props.put("hibernate.ejb.naming_strategy", ImprovedNamingStrategy.class.getName());
        return props;
    }

    @Bean
    public javax.validation.Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        hibernateJpaVendorAdapter.setShowSql(networkParams.isShowSql());
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws Exception {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan(PACKAGES_TO_SCAN);
        entityManager.setJpaPropertyMap(this.jpaProperties());
        entityManager.setJpaVendorAdapter(this.jpaVendorAdapter());
        return entityManager;
    }
}
