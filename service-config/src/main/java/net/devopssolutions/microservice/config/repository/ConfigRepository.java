package net.devopssolutions.microservice.config.repository;

import net.devopssolutions.microservice.config.model.ConfigEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigRepository extends JpaRepository<ConfigEntry, Long> {
}
