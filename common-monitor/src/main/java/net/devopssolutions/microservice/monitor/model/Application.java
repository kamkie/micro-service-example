package net.devopssolutions.microservice.monitor.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * The domain model for all registered application at the spring boot admin application.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String url;
    private final String name;

    public Application(String url, String name) {
        this(url, name, null);
    }

    @JsonCreator
    public Application(@JsonProperty(value = "url", required = true) String url,
                       @JsonProperty(value = "name", required = true) String name, @JsonProperty("id") String id) {
        this.url = url.replaceFirst("/+$", "");
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", url=" + url + ", name=" + name + "]";
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Application other = (Application) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!url.equals(other.url)) {
            return false;
        }
        return true;
    }
}