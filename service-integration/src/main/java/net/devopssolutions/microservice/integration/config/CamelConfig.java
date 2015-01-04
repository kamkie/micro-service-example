package net.devopssolutions.microservice.integration.config;

import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {

    Logger logger = LoggerFactory.getLogger(CamelConfig.class);

    @Bean
    CamelContextConfiguration contextConfiguration() {
        logger.info("init camel context");
        return camelContext -> {

        };
    }

}
