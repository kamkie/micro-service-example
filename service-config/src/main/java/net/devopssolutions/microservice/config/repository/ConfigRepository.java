package net.devopssolutions.microservice.config.repository;

import net.devopssolutions.microservice.config.model.ConfigEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

@Component
@RepositoryRestResource
public interface ConfigRepository extends CrudRepository<ConfigEntry, Long> {
}
