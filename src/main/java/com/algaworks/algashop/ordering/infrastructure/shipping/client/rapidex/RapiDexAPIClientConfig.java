package com.algaworks.algashop.ordering.infrastructure.shipping.client.rapidex;

import java.sql.ClientInfoStatus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RapiDexAPIClientConfig {

    @Bean
    public RapiDexAPIClient rapidexApiClient(
            RestClient.Builder builder,
            @Value("${algashop.integrations.rapidex.url}") String rapiDexUrl) {
        RestClient restClient = builder.baseUrl(rapiDexUrl)
        .requestFactory(generateClientAdapater.create(restClient))
        .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();
        return proxyFactory.createClient(RapiDexAPIClient.class);
    }

    private ClientHttpRequestFactory generateClientAdapater(RestClient restClient) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(Duration.ofSeconds(7));
        factory.setConnectTimeout(Duration.ofSeconds(7));
        return factory;
}
}
