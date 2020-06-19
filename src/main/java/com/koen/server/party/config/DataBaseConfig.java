package com.koen.server.party.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jca.support.LocalConnectionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@EnableJpaRepositories("com.koen.server.party.repository")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan("com.koen.server.party")
public class DataBaseConfig {

    @Resource
    private Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(environment.getRequiredProperty("db.entity.package"));
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getHibernateProperties());
        return em;
    }

    private Properties getHibernateProperties() {
        try {
            Properties properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("hibernate.properties");
            properties.load(inputStream);
            return properties;
        }
        catch (Exception ex){
            throw new IllegalArgumentException("Can't find 'hibernate' classpath", ex);
        }
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(environment.getRequiredProperty("db.url"));
        basicDataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));
        basicDataSource.setUsername(environment.getRequiredProperty("db.username"));
        basicDataSource.setPassword(environment.getRequiredProperty("db.password"));
        basicDataSource.setInitialSize(Integer.valueOf(environment.getRequiredProperty("db.initialSize")));
        basicDataSource.setMinIdle(Integer.valueOf(environment.getRequiredProperty("db.minIdle")));
        basicDataSource.setMaxIdle(Integer.valueOf(environment.getRequiredProperty("db.maxIdle")));
        basicDataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(environment.getRequiredProperty("db.timeBetwennEvictionRunsMillis")));
        basicDataSource.setMinEvictableIdleTimeMillis(Long.valueOf(environment.getRequiredProperty("db.minEvictableIndleTimeMillis")));
        basicDataSource.setTestOnBorrow(Boolean.valueOf(environment.getRequiredProperty("db.testOnBorrow")));
        basicDataSource.setValidationQuery(environment.getRequiredProperty("db.validationQuery"));
        return basicDataSource;
    }
}
