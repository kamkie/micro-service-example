/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.devopssolutions.microservice.monitor.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import net.devopssolutions.microservice.monitor.model.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class RegistryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryController.class);

    @Value("${eureka.client.serviceUrl.defaultZone}")
    String eurekaUrl;

    @Value("${eureka.username:user}")
    String eurekaUser;

    @Value("${eureka.password}")
    String eurekaPassword;

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping(value = "/api/application/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable String id) {
        LOGGER.debug("Deliver registered application with ID '{}'", id);

        Application application = getInstanceApplication(discoveryClient.getApplication(getAppNameFromId(id)), id);
        if (application != null) {
            return new ResponseEntity<>(application, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(application, HttpStatus.NOT_FOUND);
        }
    }

    private String getAppNameFromId(String id) {
        if (id != null) {
            String[] splitId = id.split(":");
            if (splitId != null && splitId.length > 1) {
                return splitId[1];
            }
        }
        return null;
    }


    /**
     * List all registered applications with name
     *
     * @return List.
     */
    @RequestMapping(value = "/api/applications", method = RequestMethod.GET)
    public Collection<Application> applications(@RequestParam(value = "name", required = false) String name) {
        LOGGER.debug("Deliver registered applications with name= {}", name);
        if (name == null || name.isEmpty()) {
            return getInstancesApplications(discoveryClient.getApplications());
        } else {
            return getInstancesApplication(discoveryClient.getApplication(name));
        }
    }

    Collection<Application> getInstancesApplications(com.netflix.discovery.shared.Applications applications) {
        List<Application> applicationList = new ArrayList<>();
        for (com.netflix.discovery.shared.Application application : applications.getRegisteredApplications()) {
            applicationList.addAll(getInstancesApplication(application));
        }
        return applicationList;
    }

    Collection<Application> getInstancesApplication(com.netflix.discovery.shared.Application application) {
        return application.getInstances().stream()
                .map(RegistryController::mapInstanceInfoToApplication)
                .collect(Collectors.toList());
    }

    private static Application mapInstanceInfoToApplication(InstanceInfo instance) {
        String actuatorUrl = instance.getStatusPageUrl().substring(0, instance.getStatusPageUrl().lastIndexOf('/'));
        return new Application(actuatorUrl, instance.getAppName(), instance.getId());
    }

    Application getInstanceApplication(com.netflix.discovery.shared.Application application, String id) {
        Optional<Application> applicationOptional = application.getInstances().stream()
                .map(RegistryController::mapInstanceInfoToApplication)
                .filter(app -> app.getId().equalsIgnoreCase(id))
                .findFirst();
        return applicationOptional.isPresent() ? applicationOptional.get() : new Application("", "", id);
    }

}
