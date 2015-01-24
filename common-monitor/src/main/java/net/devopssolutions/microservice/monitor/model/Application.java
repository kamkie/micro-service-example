package net.devopssolutions.microservice.monitor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Application implements Serializable {

    private final String id;
    private final String url;
    private final String name;

    public Application(String url, String name) {
        this(url, name, null);
    }

    @JsonCreator
    public Application(@JsonProperty(value = "url", required = false) String url,
                       @JsonProperty(value = "name", required = false) String name, @JsonProperty("id") String id) {
        this.url = url;
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

}
