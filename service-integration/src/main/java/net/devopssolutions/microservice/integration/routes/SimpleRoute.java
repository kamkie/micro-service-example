package net.devopssolutions.microservice.integration.routes;

import org.apache.camel.builder.RouteBuilder;

public class SimpleRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("{{route.simple.from}}").to("{{route.simple.to}}");
    }

}
