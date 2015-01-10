package net.devopssolutions.microservice.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.persistence.EntityManagerFactory;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "net.devopssolutions.microservice")
@EnableConfigServer
@EnableDiscoveryClient
public class BootConfig extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BootConfig.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootConfig.class);
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
