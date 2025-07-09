package com.algaworks.algashop.ordering.domain.valueobject.id;

import java.util.Objects;

import io.hypersistence.tsid.TSID;

public record OrderId(
    TSID value


) {
    public OrderId() {
        Objects.requireNonNull(value, "Order ID cannot be null");
    }

    public OrderId(Long value) {
        this(TSID.from(value));
    }

    public OrderId(String value) {
        this(TSID.from(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
