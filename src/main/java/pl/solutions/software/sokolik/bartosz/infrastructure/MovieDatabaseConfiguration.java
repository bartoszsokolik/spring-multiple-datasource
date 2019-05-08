package pl.solutions.software.sokolik.bartosz.infrastructure;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pl.solutions.software.sokolik.bartosz.movie.domain",
        entityManagerFactoryRef = "movieEntityManagerFactory",
        transactionManagerRef = "movieTransactionManager")
public class MovieDatabaseConfiguration {

    @Bean(name = "movieDatasource")
    @ConfigurationProperties(prefix = "spring.movie.datasource")
    public DataSource movieDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "movieEntityManagerFactory")
    @DependsOn("movieLiquibase")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                       @Qualifier("movieDatasource") DataSource dataSource,
                                                                       JpaProperties jpaProperties) {
        return builder.dataSource(dataSource)
                .packages("pl.solutions.software.sokolik.bartosz.movie.domain")
                .persistenceUnit("movie")
                .properties(jpaProperties.getProperties())
                .build();
    }

    @Bean(name = "movieTransactionManager")
    public PlatformTransactionManager platformTransactionManager(@Qualifier("movieEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean(name = "movieProperties")
    @ConfigurationProperties(prefix = "spring.movie.datasource.liquibase")
    public LiquibaseProperties movieProperties() {
        return new LiquibaseProperties();
    }

    @Bean(name = "movieLiquibase")
    public SpringLiquibase movieLiquibase() {
        return springLiquibase(movieDatasource(), movieProperties());
    }

    public SpringLiquibase springLiquibase(@Qualifier("movieDatasource") DataSource dataSource, @Qualifier("movieProperties") LiquibaseProperties liquibaseProperties) {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog(liquibaseProperties.getChangeLog());
        return springLiquibase;
    }
}
