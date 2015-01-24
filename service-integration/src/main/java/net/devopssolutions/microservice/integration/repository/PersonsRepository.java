package net.devopssolutions.microservice.integration.repository;

import net.devopssolutions.microservice.integration.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonsRepository extends MongoRepository<Person, String> {
}
