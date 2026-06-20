package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.exceptionhandler;

public class BadGatewayException extends RuntimeException {
    public BadGatewayException() {
    }

    public BadGatewayException(String message) {
        super(message);
    }

    public BadGatewayException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class ClientErrorException extends BadGatewayException {
        public ClientErrorException(String message) {
            super(message);
        }

        public ClientErrorException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ServerErrorException extends BadGatewayException {
        public ServerErrorException(String message) {
            super(message);
        }

        public ServerErrorException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
