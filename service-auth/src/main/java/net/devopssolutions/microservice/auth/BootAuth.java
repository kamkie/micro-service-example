package net.devopssolutions.microservice.auth;

import net.devopssolutions.microservice.security.AuthDetailsRestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "net.devopssolutions.microservice", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AuthDetailsRestService.class)})
@SpringApplicationConfiguration
@EntityScan
@EnableJpaRepositories
@EnableDiscoveryClient
//@EnableCaching
public class BootAuth extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BootAuth.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootAuth.class);
    }
}
