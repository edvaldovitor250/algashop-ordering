package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.repository.Customers;
import com.algaworks.algashop.ordering.domain.model.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.dissaembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomersPersistenceProvider implements Customers {

    private final CustomerPersistenceEntityRepository repository;
    private final EntityManager entityManager;
    private final CustomerPersistenceEntityAssembler assembler = new CustomerPersistenceEntityAssembler();
    private final CustomerPersistenceEntityDisassembler disassembler = new CustomerPersistenceEntityDisassembler();

    @Transactional
    public void save(Customer customer) {
        CustomerPersistenceEntity entity = assembler.toEntity(customer);
        CustomerPersistenceEntity saved = repository.save(entity);

        try {
            Field versionField = Customer.class.getDeclaredField("version");
            versionField.setAccessible(true);
            versionField.set(customer, saved.getVersion());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to set version via reflection", e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<Customer> findById(CustomerId id) {
        return repository.findById(id.value())
                .map(disassembler::toDomain);
    }

    @Transactional(readOnly = true)
    public boolean existsById(CustomerId id) {
        return repository.existsById(id.value());
    }

    @Override
    public Optional<Customer> ofId(CustomerId customerId) {
        return Optional.empty();
    }

    @Override
    public boolean exists(CustomerId customerId) {
        return false;
    }

    @Override
    public void add(Customer aggregateRoot) {

    }

    @Override
    public Long count() {
        return 0L;
    }

    @Override
    public Optional<Customer> ofEmail(Email email) {
       return repository.findByEmail(email.value())
                .map(disassembler::toDomain);
    }



}
