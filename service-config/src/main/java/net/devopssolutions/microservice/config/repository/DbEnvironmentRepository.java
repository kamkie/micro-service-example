package net.devopssolutions.microservice.config.repository;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.devopssolutions.microservice.config.model.ConfigEntry;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.Environment;
import org.springframework.cloud.config.PropertySource;
import org.springframework.cloud.config.server.ConfigServerProperties;
import org.springframework.cloud.config.server.EnvironmentRepository;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableConfigurationProperties(ConfigServerProperties.class)
public class DbEnvironmentRepository implements EnvironmentRepository {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public Environment findOne(String application, String env, String label) {
        Environment result = new Environment(env, label);

        String[] profiles = env.split(",");
        profiles = ArrayUtils.add(profiles, "default");
        String[] names = new String[]{application, "application"};

        Table<String, String, Map<String, String>> sources = HashBasedTable.create(names.length, profiles.length);

        for (String profile : profiles) {
            for (String app : names) {
                Map<String, String> configs = new HashMap<>();
                PropertySource propertySource = new PropertySource(app + "-" + profile, configs);
                result.add(propertySource);
                sources.put(app, profile, configs);
            }
        }

        List<ConfigEntry> configList = hibernateTemplate.findByExample(new ConfigEntry());

        for (ConfigEntry configEntry : configList) {
            Map<String, String> configs = sources.get(configEntry.getApplicationName(), configEntry.getProfile());
            configs.put(configEntry.getKey(), configEntry.getValue());
        }

        return result;
    }
}
