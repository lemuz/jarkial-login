package com.jarkial.login.configuration.database;

import com.jarkial.login.configuration.filter.HibernateStatisticsInterceptor;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {
        "com.jarkial.login.model",
        "com.jarkial.login.repositories"
    }
)
public class DatabaseConfig {

    public final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Environment env;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${jdbc.jndi}")
    private String jdbcJndi;

    @Primary
    @Bean(name = "datasource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory( 
    EntityManagerFactoryBuilder builder,
    @Qualifier("datasource") DataSource dataSource,
    JpaProperties properties){
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.putAll(properties.getProperties());
        jpaProperties.putAll(additionalProperties());
        jpaProperties.put("hibernate.session_factory.interceptor", hibernateInterceptor());
        return builder.dataSource(dataSource)
        .packages("com.jarkial.login.model")
        .properties(jpaProperties)
        .persistenceUnit("com-api-jarkial-login_PU")
        .build();
    }

    @Bean
    public HibernateStatisticsInterceptor hibernateInterceptor() {
        return new HibernateStatisticsInterceptor();
    }

    @Primary
    @Bean(name= "transactionManager")
    public PlatformTransactionManager transactionManager(
        @Qualifier("entityManagerFactory") EntityManagerFactory
         entityManagerFactory
    ){
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    @Autowired
    public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory){
        HibernateTemplate hibernateTemplate = new HibernateTemplate(sessionFactory);
        return hibernateTemplate;
    }

    @Autowired
    @Bean(name= "sessionFactory")
    public SessionFactory sessionFactory(DataSource dataSource) throws Exception{
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(false);
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setPackagesToScan("com.jarkial.login.model");
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(getHibernateProperties());
        factoryBean.afterPropertiesSet();
        SessionFactory sf = factoryBean.getObject();
        logger.info("## getSessionFactory: " + sf);
        return sf;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        return properties;
    }

    private Map additionalProperties() {
        Map properties = new LinkedHashMap();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.generate_statics", env.getProperty("spring.jpa.properties.hibernate.generate_statics"));
        properties.put("hibernate.current_session_context_class", env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
        properties.put("hibernate.jdbc.lob.non_contextual_creation", env.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));
        properties.put("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
        properties.put("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
        properties.put("hibernate.ejb.interceptor", hibernateInterceptor());
        return properties;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }   
}