package net.devopssolutions.microservice.integration.rutes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class SimpleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("{{route.simple.from}}").to("{{route.simple.to}}");
    }

}
