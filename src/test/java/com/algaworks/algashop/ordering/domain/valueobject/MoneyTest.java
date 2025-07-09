package com.algaworks.algashop.ordering.domain.valueobject;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void shouldCreateMoneyWithValidBigDecimal() {
        Money money = new Money(new BigDecimal("100.234"));
        assertThat(money.value()).isEqualTo(new BigDecimal("100.23"));
    }

    @Test
    void shouldThrowExceptionWhenValueIsNull() {
        assertThatThrownBy(() -> new Money((BigDecimal) null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Money value cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenValueIsNegative() {
        assertThatThrownBy(() -> new Money(new BigDecimal("-10.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Money value cannot be negative");
    }

    @Test
    void shouldAddTwoMoneyObjects() {
        Money a = new Money(new BigDecimal("10.00"));
        Money b = new Money(new BigDecimal("5.00"));
        Money result = a.add(b);
        assertThat(result.value()).isEqualByComparingTo("15.00");
    }

    @Test
    void shouldMultiplyMoneyByQuantity() {
        Money money = new Money(new BigDecimal("10.00"));
        Quantity quantity = new Quantity(3);
        Money result = money.multiply(quantity);
        assertThat(result.value()).isEqualByComparingTo("30.00");
    }

    @Test
    void shouldDivideMoney() {
        Money a = new Money(new BigDecimal("20.00"));
        Money b = new Money(new BigDecimal("4.00"));
        Money result = a.divide(b);
        assertThat(result.value()).isEqualByComparingTo("5.00");
    }

    @Test
    void shouldCompareMoneyCorrectly() {
        Money a = new Money(new BigDecimal("10.00"));
        Money b = new Money(new BigDecimal("15.00"));
        assertThat(a.compareTo(b)).isLessThan(0);
    }
}
