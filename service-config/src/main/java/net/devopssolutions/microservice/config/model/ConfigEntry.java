package net.devopssolutions.microservice.config.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ConfigEntry {

    @Id
    @GeneratedValue
    private Long id;

    private String applicationName;

    private String profile;

    private String label;

    private String key;

    private String value;

    public ConfigEntry() {
    }

    public ConfigEntry(String applicationName, String profile, String label, String key, String value) {
        this.applicationName = applicationName;
        this.profile = profile;
        this.label = label;
        this.key = key;
        this.value = value;
    }

    private ConfigEntry(Builder builder) {
        setId(builder.id);
        setApplicationName(builder.applicationName);
        setProfile(builder.profile);
        setLabel(builder.label);
        setKey(builder.key);
        setValue(builder.value);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(ConfigEntry copy) {
        Builder builder = new Builder();
        builder.id = copy.id;
        builder.applicationName = copy.applicationName;
        builder.profile = copy.profile;
        builder.label = copy.label;
        builder.key = copy.key;
        builder.value = copy.value;
        return builder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static final class Builder {
        private Long id = null;
        private String applicationName = null;
        private String profile = null;
        private String label = null;
        private String key = null;
        private String value = null;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withApplicationName(String applicationName) {
            this.applicationName = applicationName;
            return this;
        }

        public Builder withProfile(String profile) {
            this.profile = profile;
            return this;
        }

        public Builder withLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public ConfigEntry build() {
            return new ConfigEntry(this);
        }
    }
}
