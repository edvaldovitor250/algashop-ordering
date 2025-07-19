package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

public interface AggregateRoot <ID>{
    OrderId id();
}
