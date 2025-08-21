package com.algaworks.algashop.ordering.domain.model.order.event;

import com.algaworks.algashop.ordering.domain.model.DomainEvent;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;

import java.time.OffsetDateTime;

public record OrderPaidEvent(OrderId orderId,
                             com.algaworks.algashop.ordering.domain.model.customer.CustomerId customerId, OffsetDateTime occurredOn) implements DomainEvent {}
