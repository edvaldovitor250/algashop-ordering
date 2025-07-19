package com.algaworks.algashop.ordering.domain.model.valueobject;

public record Quantity(int value) implements Comparable<Quantity> {

    public static final Quantity ZERO = new Quantity(0);

    public Quantity {
        if (value < 0) {
            throw new IllegalArgumentException("Quantity must be zero or positive");
        }
    }

    public Quantity add(Quantity other) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }
        return new Quantity(this.value + other.value);
    }

    @Override
    public int compareTo(Quantity other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot compare to null quantity");
        }
        return Integer.compare(this.value, other.value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
