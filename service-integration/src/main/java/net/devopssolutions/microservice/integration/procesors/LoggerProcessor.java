package net.devopssolutions.microservice.integration.procesors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;


@Component
@RefreshScope
public class LoggerProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(LoggerProcessor.class);

    @Value("${route.simple.from}")
    String uriFrom;

    @Value("${route.simple.to}")
    String uriTo;
    
    @Value("${route.simple.something}")
    String something;

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("process {} from {}", exchange.getIn().getMessageId(), exchange.getFromEndpoint().getEndpointUri());
        logger.info("from {} to {} something {}", uriFrom, uriTo, something);
    }
}
