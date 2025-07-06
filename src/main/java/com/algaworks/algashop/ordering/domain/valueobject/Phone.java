
package com.algaworks.algashop.ordering.domain.valueobject;

public record Phone(String value) {

    public Phone(String value) {
        if (value == null || !value.matches("^\\+?[0-9]{10,15}$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
