package net.devopssolutions.microservice.configuration;

import net.devopssolutions.microservice.components.RibbonClientHttpRequestFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@AutoConfigureAfter(RibbonAutoConfiguration.class)
@AutoConfigureBefore(LoadBalancerAutoConfiguration.class)
public class RibbonConfiguration {

    @Bean
    public RestTemplate restTemplate(SpringClientFactory springClientFactory, LoadBalancerClient loadBalancerClient) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new RibbonClientHttpRequestFactory(springClientFactory, loadBalancerClient));
        return restTemplate;
    }

}
