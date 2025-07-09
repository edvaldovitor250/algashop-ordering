package com.algaworks.algashop.ordering.domain.valueobject;

public record ProductName(String value) {

    public ProductName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("Product name cannot exceed 100 characters");
        }
    }

    @Override
    public String toString() {
        return value;
    }

}
