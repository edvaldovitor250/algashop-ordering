package com.algaworks.algashop.ordering.domain.model.valueobject;

public record Email(String value) {

    public Email(String value) {
        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
