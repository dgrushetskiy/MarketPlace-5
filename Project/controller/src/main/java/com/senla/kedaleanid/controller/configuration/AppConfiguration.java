package com.senla.kedaleanid.controller.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by earthofmarble on Sep, 2019
 */
@Configuration
@ComponentScan(basePackages = {"com.senla.kedaleanid.controller.*", "com.senla.kedaleanid.service.*",
        "com.senla.kedaleanid.utility.mapper", "com.senla.kedaleanid.dal.*", "com.senla.kedaleanid.utility.mail"})
@EnableTransactionManagement
public class AppConfiguration {

    private static final String[] PACKAGES_TO_SCAN = {"com.senla.kedaleanid.model.*"};

    private static final Properties hibernateProperties;

    static {
        hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.connection.charSet", "UTF-8");
        hibernateProperties.setProperty("hibernate.connection.characterEncoding", "UTF-8");
        hibernateProperties.setProperty("hibernate.connection.useUnicode", "true");
    }

    //TODO разобраться!!!! совсем этим

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/advertisements?serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource());
        lef.setJpaVendorAdapter(jpaVendorAdapter());
        lef.setPackagesToScan(PACKAGES_TO_SCAN);
        lef.setJpaProperties(hibernateProperties);
        lef.setPersistenceUnitName("persistence");
        return lef;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionBean = new LocalSessionFactoryBean();
        sessionBean.setDataSource(dataSource());
        sessionBean.setPackagesToScan(PACKAGES_TO_SCAN);
        sessionBean.setHibernateProperties(hibernateProperties);
        return sessionBean;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory().getObject());
        return new JpaTransactionManager();
    }

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("reswizcompany@gmail.com");
        mailSender.setPassword("Nokia5230mango");

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }

}
