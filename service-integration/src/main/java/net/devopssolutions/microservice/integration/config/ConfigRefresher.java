package net.devopssolutions.microservice.integration.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.config.RefreshEndpoint;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ConfigRefresher {

    private static final int REFRESH_DELAY = 100000;

    private Logger logger = LoggerFactory.getLogger(ConfigRefresher.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private RefreshEndpoint refreshEndpoint;

    @Scheduled(fixedDelay = REFRESH_DELAY)
    public void refreshConfig() {
        logger.info("============ refreshing config =============");
        String[] changes = refreshEndpoint.refresh();
        for (String change : changes) {
            logger.info("changed property: {}", change);
        }
    }

}
