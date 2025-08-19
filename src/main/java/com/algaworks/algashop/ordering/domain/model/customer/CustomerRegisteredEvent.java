
package com.algaworks.algashop.ordering.domain.model.customer;

public record CustomerRegisteredEvent(CustomerId customerId, java.time.OffsetTime registeredAt) {
}
