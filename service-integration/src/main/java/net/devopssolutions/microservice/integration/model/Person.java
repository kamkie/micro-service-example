package net.devopssolutions.microservice.integration.model;

import lombok.Data;
import lombok.experimental.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "persons")
@Data
@Builder
@SuppressWarnings("PMD")
public class Person {

    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    private Long socialNumber;

}
