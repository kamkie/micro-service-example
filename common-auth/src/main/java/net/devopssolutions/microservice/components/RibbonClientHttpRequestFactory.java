package net.devopssolutions.microservice.components;

import com.netflix.client.config.IClientConfig;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.niws.client.http.RestClient;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.net.URI;

public class RibbonClientHttpRequestFactory extends org.springframework.cloud.netflix.ribbon.RibbonClientHttpRequestFactory {

    private final LoadBalancerClient loadBalancer;
    private final SpringClientFactory clientFactory;

    public RibbonClientHttpRequestFactory(SpringClientFactory clientFactory, LoadBalancerClient loadBalancer) {
        super(clientFactory, loadBalancer);
        this.loadBalancer = loadBalancer;
        this.clientFactory = clientFactory;
    }

    @Override
    @SuppressWarnings("deprecation")
    public ClientHttpRequest createRequest(URI originalUri, HttpMethod httpMethod) throws IOException {
        String serviceId = originalUri.getHost();
        ServiceInstance instance = loadBalancer.choose(serviceId);
        if (instance == null) {
            throw new IllegalStateException("No instances available for " + serviceId);
        }
        URI uri = loadBalancer.reconstructURI(instance, originalUri);

        IClientConfig clientConfig = clientFactory.getClientConfig(instance.getServiceId());
        RestClient client = clientFactory.getClient(instance.getServiceId(), RestClient.class);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .verb(HttpRequest.Verb.valueOf(httpMethod.name()))
                .build();

        return new RibbonHttpRequest(request, client, clientConfig);
    }

    @SuppressWarnings("deprecation")
    public class RibbonHttpRequest extends org.springframework.cloud.netflix.ribbon.RibbonClientHttpRequestFactory.RibbonHttpRequest {

        private HttpRequest request;
        private RestClient client;
        private IClientConfig config;

        public RibbonHttpRequest(HttpRequest request, RestClient client, IClientConfig config) {
            super(request, client, config);
            this.request = request;
            this.client = client;
            this.config = config;
        }

        @Override
        protected ClientHttpResponse executeInternal(HttpHeaders headers) throws IOException {
            return loadBalancer.execute(this.config.getClientName(), instance -> {
                HttpHeaders httpHeaders = getHeaders();
                HttpRequest.Builder builder = HttpRequest.newBuilder()
                        .uri(request.getUri())
                        .verb(request.getVerb());
                httpHeaders.forEach((header, strings) -> strings.forEach(value -> builder.header(header, value)));

                HttpResponse response = client.execute(builder.build(), config);
                return new RibbonHttpResponse(response);
            });
        }

    }

}
