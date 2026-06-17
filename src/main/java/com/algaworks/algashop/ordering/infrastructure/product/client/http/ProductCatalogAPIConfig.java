package com.algaworks.algashop.ordering.infrastructure.product.client.http;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ProductCatalogAPIConfig {

    @Bean
    public ProductCatalogAPIClient productCatalogAPIClient(
            RestClient.Builder builder,
            @Value("${algashop.integrations.product-catalog.url}") String url,
            @Value("${algashop.integrations.product-catalog.connect-timeout:3s}") Duration connectTimeout,
            @Value("${algashop.integrations.product-catalog.read-timeout:5s}") Duration readTimeout
    ) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);

        RestClient restClient = builder
                .baseUrl(url)
                .requestFactory(requestFactory)
                .defaultStatusHandler(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            throw new ProductCatalogClientException(
                                    "Erro 4xx ao chamar Product Catalog API. Status: "
                                            + response.getStatusCode()
                            );
                        }
                )
                .defaultStatusHandler(
                        HttpStatusCode::is5xxServerError,
                        (request, response) -> {
                            throw new ProductCatalogServerException(
                                    "Erro 5xx ao chamar Product Catalog API. Status: "
                                            + response.getStatusCode()
                            );
                        }
                )
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(adapter).build();

        return proxyFactory.createClient(ProductCatalogAPIClient.class);
    }

}