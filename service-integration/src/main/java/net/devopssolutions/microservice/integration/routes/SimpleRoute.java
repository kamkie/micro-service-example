package net.devopssolutions.microservice.integration.routes;

import net.devopssolutions.microservice.integration.procesors.LoggerProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class SimpleRoute extends RouteBuilder {

    private Logger logger = LoggerFactory.getLogger(SimpleRoute.class);

    @Value("${route.simple.from}")
    private String uriFrom;

    @Value("${route.simple.to}")
    private String uriTo;

    @Autowired
    private LoggerProcessor loggerProcessor;

    @Override
    public void configure() throws Exception {
        logger.info("from {} to {}", uriFrom, uriTo);

        from(uriFrom)
                .routeId(SimpleRoute.class.getCanonicalName())
                .process(loggerProcessor)
                .to(uriTo);
    }

}
