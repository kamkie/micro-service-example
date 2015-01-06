package net.devopssolutions.microservice.integration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.client.RefreshEndpoint;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ConfigRefresher {

    Logger logger = LoggerFactory.getLogger(ConfigRefresher.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    RefreshEndpoint refreshEndpoint;

    @Scheduled(fixedDelay = 10000)
    public void refreshConfig() {
        logger.info("============ refreshing config =============");
        String[] changes = refreshEndpoint.refresh();
        for (String change : changes) {
            logger.info("changed property: {}", change);
        }
    }

}
