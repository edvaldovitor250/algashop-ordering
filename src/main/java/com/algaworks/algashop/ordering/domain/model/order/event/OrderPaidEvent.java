package com.algaworks.algashop.ordering.domain.model.order.event;

import com.algaworks.algashop.ordering.domain.model.DomainEvent;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;

import java.time.OffsetDateTime;

public record OrderPaidEvent(OrderId orderId, OffsetDateTime occurredOn) implements DomainEvent {}
