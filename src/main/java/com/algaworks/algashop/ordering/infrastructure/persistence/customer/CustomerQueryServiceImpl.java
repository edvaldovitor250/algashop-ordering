package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import com.algaworks.algashop.ordering.application.customer.management.CustomerOutput;
import com.algaworks.algashop.ordering.application.customer.management.CustomerQueryService;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CustomerQueryServiceImpl  implements CustomerQueryService {

    private final CustomerPersistenceEntityRepository repository;
    private final Mapper mapper;

    @Override
    public CustomerOutput findById(UUID customerId) {
        CustomerPersistenceEntity customer = repository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return mapper.convert(customer, CustomerOutput.class);
    }
}
