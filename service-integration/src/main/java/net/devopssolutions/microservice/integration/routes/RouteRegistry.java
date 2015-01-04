package net.devopssolutions.microservice.integration.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RefreshScope
public class RouteRegistry {

    @Autowired
    SimpleRoute simpleRoute;

    public Map<Class<? extends RouteBuilder>, RouteBuilder> getRoutes() {
        Map<Class<? extends RouteBuilder>, RouteBuilder> routes = new HashMap<>();
        routes.put(SimpleRoute.class, simpleRoute);
        return routes;
    }

}
