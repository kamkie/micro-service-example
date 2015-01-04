package net.devopssolutions.microservice.integration.controller;

import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.impl.DefaultPackageScanClassResolver;
import org.apache.camel.spi.PackageScanClassResolver;
import org.apache.camel.spring.PackageScanRouteBuilderFinder;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SLF4JLogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.liquibase.SpringPackageScanClassResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);
    private String PACKAGE_TO_RELOAD = "net.devopssolutions.microservice.integration.routes";

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    CamelContext camelContext;

    @RequestMapping("/")
    public void refresh() {
        logger.info("=================================== refresh camel");
//        try {
//            camelContext.stop();
//        } catch (Exception e) {
//            logger.error("could not stop camel", e);
//        }
//        try {
//            camelContext.start();
//        } catch (Exception e) {
//            logger.error("could not start camel", e);
//        }
        try {
            onReload((SpringCamelContext) camelContext);
        } catch (Exception e) {
            logger.error("error onReload", e);
        }
    }

    public void onReload(SpringCamelContext camelContext) throws Exception {
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
//            PackageScanClassResolver springPackageScanClassResolver = new DefaultPackageScanClassResolver();
//            rbf = new PackageScanRouteBuilderFinder(camelContext, new String[]{PACKAGE_TO_RELOAD},
//                    this.getClass().getClassLoader(), null, springPackageScanClassResolver);
//            List<RoutesBuilder> builders = new ArrayList<>();
//            rbf.appendBuilders(builders);

//            for (RoutesBuilder rb : builders) {
//                camelContext.addRoutes(rb);
//            }

            logger.info("======= after reload ========");
            printRoutes();
        }
    }

    void printRoutes() {
        for (Route r : camelContext.getRoutes()) {
            logger.info("route=" + r.getEndpoint().getEndpointUri().toString());
        }
    }

}
