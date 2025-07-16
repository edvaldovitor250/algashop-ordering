package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;

public interface AggregateRoot <ID>{
    OrderId id();
}
