package net.devopssolutions.microservice.auth;

import net.devopssolutions.microservice.security.AuthDetailsRestService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.persistence.EntityManagerFactory;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "net.devopssolutions.microservice", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AuthDetailsRestService.class)})
@EntityScan
@EnableJpaRepositories
@EnableDiscoveryClient
@EnableCaching
public class BootAuth extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BootAuth.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootAuth.class);
    }

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("passwordEncoder");
        return cacheManager;
    }

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    EntityManagerFactory factory;

    @Bean
    public HibernateTemplate hibernateTemplate() {
        return new HibernateTemplate(sessionFactory());
    }

    @Bean
    public SessionFactory sessionFactory() {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        return factory.unwrap(SessionFactory.class);
    }

}
