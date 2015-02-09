package net.devopssolutions.microservice.monitor.repository;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.shared.Pair;
import com.netflix.eureka.PeerAwareInstanceRegistry;
import net.devopssolutions.microservice.monitor.model.Application;
import net.devopssolutions.microservice.monitor.model.Instance;
import net.devopssolutions.microservice.monitor.model.InstanceHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Eureka registry implementation of application repository
 */
public class EurekaRepository implements ApplicationRepository {

    @Value("${spring.cloud.dashboard.turbine.url:http://localhost:${server.port}/turbine.stream}")
    private String turbineUrl;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public Collection<Application> findAll() {
        return discoveryClient.getApplications().getRegisteredApplications().stream()
                .map(EurekaRepository::toApplication)
                .collect(Collectors.toList());
    }

    @Override
    public Application findByName(String name) {
        return toApplication(discoveryClient.getApplications().getRegisteredApplications(name));
    }

    @Override
    public String getApplicationCircuitBreakerStreamUrl(String name) {
        if (findByName(name) == null) {
            return null;
        }
        return turbineUrl + "?cluster=" + name;
    }

    @Override
    public String getInstanceCircuitBreakerStreamUrl(String instanceId) {
        String url = getInstanceManagementUrl(instanceId);
        if (url == null) {
            return null;
        }
        return url + "/hystrix.stream";
    }

    @Override
    public Instance findInstance(String id) {
        return instanceInfoToInstance(findInstanceInfo(id));
    }

    @Override
    public String getInstanceManagementUrl(String id) {

        InstanceInfo info = findInstanceInfo(id);
        if (info == null) {
            return null;
        }

        String url = info.getHomePageUrl();
        if (info.getMetadata().containsKey("managementPath")) {
            url += info.getMetadata().get("managementPath");
        }

        return url;
    }

    @Override
    public List<InstanceHistory> getCanceledInstanceHistory() {
        return PeerAwareInstanceRegistry.getInstance().getLastNCanceledInstances().stream()
                .map(EurekaRepository::toInstanceHistory)
                .collect(Collectors.toList());
    }

    @Override
    public List<InstanceHistory> getRegisteredInstanceHistory() {
        return PeerAwareInstanceRegistry.getInstance().getLastNRegisteredInstances().stream()
                .map(EurekaRepository::toInstanceHistory)
                .collect(Collectors.toList());
    }

    private InstanceInfo findInstanceInfo(String id) {
        String[] instanceIds = id.split("_", 2);
        return PeerAwareInstanceRegistry.getInstance().getInstanceByAppAndId(instanceIds[0], instanceIds[1].replaceAll("_", "."));
    }

    public static Application toApplication(com.netflix.discovery.shared.Application app) {
        if (app == null) {
            return null;
        }
        return new Application(app.getName(), app.getInstances().stream()
                .map(EurekaRepository::instanceInfoToInstance)
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList()));
    }

    public static Instance instanceInfoToInstance(InstanceInfo instance) {
        if (instance == null) {
            return null;
        }
        String actuatorUrl = instance.getStatusPageUrl().substring(0, instance.getStatusPageUrl().lastIndexOf('/'));
        return new Instance(actuatorUrl, instance.getId(), instance.getAppName() + "_" + instance.getId().replaceAll("\\.", "_"), instance.getStatus().toString());
    }

    public static InstanceHistory toInstanceHistory(Pair<Long, String> history) {
        return new InstanceHistory(history.second(), new Date(history.first()));
    }
}
