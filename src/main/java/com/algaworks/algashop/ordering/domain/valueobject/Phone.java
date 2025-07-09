package com.algaworks.algashop.ordering.domain.valueobject;

public record Phone(String value) {

    private static final String PHONE_REGEX =
            "^(\\+\\d{1,3}\\s?)?(\\(?\\d{2}\\)?\\s?)?(\\d{4,5}-\\d{4})$";

    public Phone(String value) {
        if (value == null || !value.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
