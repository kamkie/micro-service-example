package net.devopssolutions.microservice.config.repository;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.devopssolutions.microservice.config.model.ConfigEntry;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.ConfigServerProperties;
import org.springframework.cloud.config.server.EnvironmentRepository;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@EnableConfigurationProperties(ConfigServerProperties.class)
public class DbEnvironmentRepository implements EnvironmentRepository {

    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Override
    public String getDefaultLabel() {
        return "master";
    }

    @Override
    public Environment findOne(String application, String env, String label) {
        Environment result = new Environment(env, label);

        List<String> profileList = Arrays.stream(env.split(",")).collect(Collectors.toList());
        profileList.add("default");

        String[] names = new String[]{application, "application"};

        Table<String, String, Map<String, String>> sources = HashBasedTable.create(names.length, profileList.size());

        for (String profile : profileList) {
            for (String app : names) {
                Map<String, String> configs = new HashMap<>();
                PropertySource propertySource = new PropertySource(app + "-" + profile, configs);
                result.add(propertySource);
                sources.put(app, profile, configs);
            }
        }

        List<?> configList = hibernateTemplate.findByCriteria(
                DetachedCriteria.forClass(ConfigEntry.class)
                        .add(Restrictions.in("applicationName", names))
                        .add(Restrictions.in("profile", profileList))
                        .add(Restrictions.eq("label", label)));

        for (Object configEntryObj : configList) {
            ConfigEntry configEntry = (ConfigEntry) configEntryObj;
            Map<String, String> configs = sources.get(configEntry.getApplicationName(), configEntry.getProfile());
            configs.put(configEntry.getKey(), configEntry.getValue());
        }

        return result;
    }
}
