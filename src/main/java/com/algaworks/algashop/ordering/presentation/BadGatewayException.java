package com.algaworks.algashop.ordering.presentation;

public class BadGatewayException extends RuntimeException {
    public BadGatewayException() {
    }

    public BadGatewayException(String message, Throwable cause) {
        super(message, cause);
    }

    public static  InternalErrorException extends BadGatewayException {
    public InternalErrorException() {
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    
}

public static  ClientErrorException extends BadGatewayException {
    public ClientErrorException() {
    }

    public ClientErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    private Exception translateException(RestClientException e) {
        if (e.getCause() instanceof SocketTimeoutException) {
            return new GatewayTimeoutException("Product Catalog API Timeout", e);
        }
        if (e instanceof HttpClientErrorException) {
            return new ClientErrorException("Product Catalog API Client Error", e);
        }
        return new BadGatewayException("Product Catalog API Bad Gateway", e);
    }


}

}
