package com.algaworks.algashop.ordering.infrastructure.adapters.out.web.shipping.client.rapidex;

import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.exceptionhandler.BadGatewayException;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.exceptionhandler.GatewayTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.retry.FrameworkRetryCircuitBreaker;
import org.springframework.cloud.circuitbreaker.retry.FrameworkRetryConfig;
import org.springframework.cloud.circuitbreaker.retry.FrameworkRetryConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.NoFallbackAvailableException;
import org.springframework.core.retry.RetryException;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.net.SocketTimeoutException;

@Component
@Slf4j
public class ResilientRapiDexAPIClient {

    private final RapiDexAPIClient rapiDexAPIClient;
    private final FrameworkRetryCircuitBreaker circuitBreaker;

    public ResilientRapiDexAPIClient(CircuitBreakerFactory<FrameworkRetryConfig,
                                                    FrameworkRetryConfigBuilder> circuitBreakerFactory,
                                    RapiDexAPIClient rapiDexAPIClient) {
        this.rapiDexAPIClient = rapiDexAPIClient;
        this.circuitBreaker = (FrameworkRetryCircuitBreaker) circuitBreakerFactory.create("rapidexCB");
    }

    @ConcurrencyLimit(10)
    public DeliveryCostResponse calculate(DeliveryCostRequest request) {
        log.info("Rapidex API CB state is {}", circuitBreaker.getCircuitBreakerPolicy().getState());

        try {
           DeliveryCostResponse response = circuitBreaker.run(() -> doCalculate(request),
                    e -> {
                        log.warn("Error when loading delivery cost for request {}, returning null", request, e);
                        return null;
                    });
            if (response == null) {
                log.info("No delivery cost found for request {}", request);
            }
            return response;
        } catch (NoFallbackAvailableException e) {
            throw unwrapException(e);
        }
    }

    private DeliveryCostResponse doInternalFallback(DeliveryCostRequest request) {
        log.warn("Using fallback for request {}", request);
        return null;
    }

    private RuntimeException unwrapException(NoFallbackAvailableException e) {
        if (e.getCause() instanceof RetryException re) {
            if (re.getCause() instanceof GatewayTimeoutException gte) {
                return gte;
            }
            if (re.getCause() instanceof BadGatewayException bge) {
                return bge;
            }
        }
        return e;
    }

    private DeliveryCostResponse doCalculate(DeliveryCostRequest request) {
        try {
            return rapiDexAPIClient.calculate(request);
        } catch (HttpClientErrorException e) {
            if (!(e instanceof HttpClientErrorException.NotFound)) {
                log.warn("Client Error when loading delivery cost {}", request, e);
            }
            return null;
        } catch (RestClientException e) {
            throw translateException(e);
        }
    }

    private RuntimeException translateException(RestClientException e) {
        if (e.getCause() instanceof SocketTimeoutException
            || e instanceof ResourceAccessException) {
            return new GatewayTimeoutException("Rapidex API Timeout", e);
        }

        if (e instanceof HttpServerErrorException) {
            return new BadGatewayException.ServerErrorException("Rapidex API Bad Gateway", e);
        }

        return new BadGatewayException("Rapidex API Bad Gateway", e);
    }

}
