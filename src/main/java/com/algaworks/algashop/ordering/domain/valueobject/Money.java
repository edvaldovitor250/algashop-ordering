package com.algaworks.algashop.ordering.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal value) implements Comparable<Money> {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money {
        if (value == null) {
            throw new IllegalArgumentException("Money value cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money value cannot be negative");
        }

        value = value.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Money multiply(Quantity quantity) {
        if (quantity == null || Integer.parseInt(String.valueOf(quantity.value())) < 1) {
            throw new IllegalArgumentException("Quantity must be >= 1");
        }
        return new Money(this.value.multiply(new BigDecimal(quantity.value())));
    }

    public Money add(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Other money cannot be null");
        }
        return new Money(this.value.add(other.value));
    }

    public Money divide(Money other) {
        if (other == null || other.value.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Cannot divide by null or zero money");
        }
        return new Money(this.value.divide(other.value, 2, RoundingMode.HALF_EVEN));
    }

    @Override
    public int compareTo(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare to null money");
        }
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return value.toPlainString(); 
    }
}
