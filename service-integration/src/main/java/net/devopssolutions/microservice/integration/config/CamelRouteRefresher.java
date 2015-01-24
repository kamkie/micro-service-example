package net.devopssolutions.microservice.integration.config;

import net.devopssolutions.microservice.integration.routes.RouteRegistry;
import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CamelRouteRefresher implements ApplicationListener<EnvironmentChangeEvent> {

    private Logger logger = LoggerFactory.getLogger(CamelRouteRefresher.class);

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private RouteRegistry routeRegistry;

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        logger.info("=================================== refresh camel ===================================");
        printRoutes();

        routeRegistry.getRoutes().forEach((routeKey, routeBuilder) -> {
            String routeId = routeKey.getCanonicalName();
            try {
                camelContext.stopRoute(routeId);
                camelContext.removeRoute(routeId);
                camelContext.addRoutes(routeBuilder);
                camelContext.startRoute(routeId);
            } catch (Exception e) {
                logger.error("could not refresh camel route {}", e, routeId);
            }
        });

        logger.info("=================================== after reload ===================================");
        logger.info("printRoutes after reload");
        printRoutes();
    }

    void printRoutes() {
        for (Route route : camelContext.getRoutes()) {
            String routeStr = String.format("route id %s from %s", route.getId(),
                    route.getEndpoint().getEndpointUri());
            logger.info(routeStr);
        }
    }
}
