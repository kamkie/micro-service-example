package net.devopssolutions.microservice.auth.repository;

import net.devopssolutions.microservice.auth.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);
}
