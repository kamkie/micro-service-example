package net.devopssolutions.microservice.integration;

import net.devopssolutions.microservice.integration.model.Person;
import net.devopssolutions.microservice.integration.repository.PersonsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.FeignClientScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "net.devopssolutions.microservice")
@EnableDiscoveryClient
@EnableCircuitBreaker
@FeignClientScan
@EnableCaching
public class BootIntegration extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(BootIntegration.class, args);
        PersonsRepository personsRepository = applicationContext.getBean(PersonsRepository.class);

        List<Person> persons = new ArrayList<>();

        persons.add(Person.builder().firstName("Kamil").lastName("Kiewisz").socialNumber(132412L + 1).build());
        persons.add(Person.builder().firstName("kamkie").lastName("kamkie").socialNumber(987123L + 2).build());

        personsRepository.save(persons);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootIntegration.class);
    }

    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager("passwordEncoder");
        return cacheManager;
    }
}
