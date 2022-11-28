package edu.jong.board.jpa.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaAuditing
@EnableTransactionManagement
public class DatabaseConfig {

	@Bean  
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    HikariConfig hikariConfig() {
        return new HikariConfig();
    } 
 
    @Bean
    DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }
    
    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

}
