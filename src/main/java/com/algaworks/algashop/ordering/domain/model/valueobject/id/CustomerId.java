package com.algaworks.algashop.ordering.domain.model.valueobject.id;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {

    public CustomerId(){
        this(UUID.randomUUID());
    }

    public CustomerId(UUID value){
        this.value = Objects.requireNonNull(value, "Customer ID cannot be null");
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
