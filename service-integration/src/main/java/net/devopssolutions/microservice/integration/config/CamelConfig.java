package net.devopssolutions.microservice.integration.config;

import net.devopssolutions.microservice.integration.routes.SimpleRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class CamelConfig {

    @Autowired
    CamelContextConfiguration contextConfiguration;

    @Autowired
    CamelContext camelContext;

    Logger logger = LoggerFactory.getLogger(CamelConfig.class);

    public void refreshAll() {
        logger.info("refresh all");
    }

    @Bean
    @RefreshScope
    CamelContextConfiguration contextConfiguration() {
        logger.info("refresh camel");
        CamelContextConfiguration camelContextConfiguration = camelContext -> {
            try {
                camelContext.stop();
            } catch (Exception e) {
                logger.error("could not stop camel", e);
            }
            try {
                camelContext.start();
            } catch (Exception e) {
                logger.error("could not start camel", e);
            }
        };
        camelContextConfiguration.beforeApplicationStart(camelContext);
        return camelContextConfiguration;
    }

    @Bean
    @RefreshScope
    RoutesBuilder myRouter() {
        logger.info("refresh route");
        return new SimpleRoute();
    }

}
