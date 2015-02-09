/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.devopssolutions.microservice.monitor.config;

import net.devopssolutions.microservice.monitor.repository.ApplicationRepository;
import net.devopssolutions.microservice.monitor.repository.EurekaRepository;
import net.devopssolutions.microservice.monitor.stream.CircuitBreakerStreamServlet;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring Cloud Dashboard WebApp configuration
 *
 * @author Julien Roy
 */
@Configuration
public class CloudDashboardConfig extends WebMvcConfigurerAdapter {

    @Bean
    @ConditionalOnClass(EurekaDiscoveryClient.class)
    @ConditionalOnMissingBean(ApplicationRepository.class)
    public EurekaRepository eurekaRepository() {
        return new EurekaRepository();
    }

    @Bean
    @Autowired
    public ServletRegistrationBean circuitBreakerStreamServlet(ApplicationRepository repository) {
        return new ServletRegistrationBean(new CircuitBreakerStreamServlet(httpClient(), repository), "/circuitBreaker.stream");
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClients.custom()
                .setMaxConnTotal(100)
                .setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(2000).build())
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setSocketTimeout(2000)
                        .setConnectTimeout(1000)
                        .setConnectionRequestTimeout(1000).build())
                .build();
    }
}
