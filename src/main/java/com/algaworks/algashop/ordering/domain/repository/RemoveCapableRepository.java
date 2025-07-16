package com.algaworks.algashop.ordering.domain.repository;

import com.algaworks.algashop.ordering.domain.entity.AggregateRoot;
import com.algaworks.algashop.ordering.domain.repository.Repository;

public interface RemoveCapableRepository<T extends AggregateRoot<ID>, ID>
        extends Repository<T, ID>
{
    void remove(T t);
    void remove(ID id);
}