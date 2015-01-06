package net.devopssolutions.microservice.integration.controller;

import net.devopssolutions.microservice.integration.routes.RouteRegistry;
import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    CamelContext camelContext;

    @RequestMapping("/")
    public String refresh() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Route route : camelContext.getRoutes()) {
            String routeStr = String.format("route id %s from %s", route.getId(),
                    route.getEndpoint().getEndpointUri());
            logger.info(routeStr);
            stringBuilder.append(routeStr);
        }

        return stringBuilder.toString();
    }

}
