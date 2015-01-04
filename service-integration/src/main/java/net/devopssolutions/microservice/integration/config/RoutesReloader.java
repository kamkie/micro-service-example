package net.devopssolutions.microservice.integration.config;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.spi.PackageScanClassResolver;
import org.apache.camel.spring.PackageScanRouteBuilderFinder;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SLF4JLogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.liquibase.SpringPackageScanClassResolver;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RefreshScope
public class RoutesReloader implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(RoutesReloader.class);
    Log log = SLF4JLogFactory.getLog(RoutesReloader.class);

    private SpringCamelContext camelContext;
    private String PACKAGE_TO_RELOAD = "net.devopssolutions.microservice.integration.routes";
    private ApplicationContext applicationContext;

    public void onReload() throws Exception {
        logger.info("on reload ");
        if (null != camelContext) {
            printRoutes();

            //stop and destroy the old camelContext
            camelContext.stop();
            camelContext.destroy();
            camelContext = null;

            //create and start a new camel context
            camelContext = SpringCamelContext.springCamelContext(applicationContext);
            camelContext.start();

            // Re-add all the RouteBuilders under PACKAGE_TO_RELOAD package
            PackageScanRouteBuilderFinder rbf;
            SpringPackageScanClassResolver springPackageScanClassResolver = new SpringPackageScanClassResolver(log);
            rbf = new PackageScanRouteBuilderFinder(camelContext, new String[]{PACKAGE_TO_RELOAD},
                    this.getClass().getClassLoader(), null, (PackageScanClassResolver) springPackageScanClassResolver);
            List<RoutesBuilder> builders = new ArrayList<>();
            rbf.appendBuilders(builders);

            for (RoutesBuilder rb : builders) {
                camelContext.addRoutes(rb);
            }

            logger.info("======= after reload ========");
            printRoutes();
        }
    }

    public void setCamelContext(CamelContext arg0) {
        camelContext = (SpringCamelContext) arg0;
    }

    void printRoutes() {
        for (Route r : camelContext.getRoutes()) {
            logger.info("route=" + r.getEndpoint().getEndpointUri().toString());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0)
            throws BeansException {
        this.applicationContext = arg0;

    }
}