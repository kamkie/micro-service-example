package net.devopssolutions.microservice.config.controller;

import net.devopssolutions.microservice.config.model.ConfigEntry;
import net.devopssolutions.microservice.config.repository.ConfigRepository;
import org.resthub.web.controller.RepositoryBasedRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config_entry")
public class ConfigController extends RepositoryBasedRestController<ConfigEntry, Long, ConfigRepository> {

    @Autowired
    @Override
    public void setRepository(ConfigRepository repository) {
        this.repository = repository;
    }
}
