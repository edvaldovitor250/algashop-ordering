package com.algaworks.algashop.ordering.domain.model.valueobject;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BirthDate(BigDecimal value) {

    public BirthDate(BigDecimal value) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Birth date must be a positive number");
        }
        this.value = value;
    }

    public BirthDate(int year, int month, int day) {
        this(new BigDecimal(year * 10000 + month * 100 + day));
    }

    public BirthDate(LocalDate of) {
        this(new BigDecimal(of.getYear() * 10000 + of.getMonthValue() * 100 + of.getDayOfMonth()));
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
