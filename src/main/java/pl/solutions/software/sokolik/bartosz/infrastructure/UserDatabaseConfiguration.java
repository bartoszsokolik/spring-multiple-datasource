package pl.solutions.software.sokolik.bartosz.infrastructure;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pl.solutions.software.sokolik.bartosz.user.domain",
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager")
public class UserDatabaseConfiguration {

    @Primary
    @Bean(name = "userDatasource")
    @ConfigurationProperties(prefix = "spring.user.datasource")
    public DataSource userDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "userEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "validate");
        return builder.dataSource(dataSource)
                .packages("pl.solutions.software.sokolik.bartosz.user.domain")
                .persistenceUnit("user")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "userTransactionManager")
    public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Primary
    @Bean(name = "userProperties")
    @ConfigurationProperties(prefix = "spring.user.datasource.liquibase")
    public LiquibaseProperties userProperties() {
        return new LiquibaseProperties();
    }

    @Primary
    @Bean(name = "liquibase")
    public SpringLiquibase userLiquibase() {
        return springLiquibase(userDatasource(), userProperties());
    }

    public SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties liquibaseProperties) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog(liquibaseProperties.getChangeLog());
        return springLiquibase;
    }
}
