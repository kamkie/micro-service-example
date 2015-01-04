package net.devopssolutions.microservice.integration.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class RouteRegistry extends HashMap<Class<? extends RouteBuilder>, RouteBuilder> {

    @Autowired
    SimpleRoute simpleRoute;

    public void refresh() {
        clear();
        put(SimpleRoute.class, simpleRoute);
    }
}
