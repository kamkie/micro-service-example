package net.devopssolutions.mikroservice.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@EnableConfigServer
@PropertySources(value = {@PropertySource("classpath:configServer.properties")})
public class BootConfig extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BootConfig.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootConfig.class);
    }
}