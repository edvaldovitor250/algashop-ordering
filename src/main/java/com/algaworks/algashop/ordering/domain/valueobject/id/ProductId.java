package com.algaworks.algashop.ordering.domain.valueobject.id;

import java.util.UUID;

public record ProductId(UUID value) {

    public ProductId() {
        this(UUID.randomUUID());
    }

    public ProductId(UUID value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
