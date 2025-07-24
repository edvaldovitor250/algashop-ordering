package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

import java.util.UUID;

public class CustomerPersistenceEntityAssembler {

    public CustomerPersistenceEntity toEntity(Customer customer) {
        return CustomerPersistenceEntity.builder()
                .id((UUID) customer.id())
                .version(customer.version())
                .firstName(customer.fullName().firstName())
                .lastName(customer.fullName().lastName())
                .email(customer.email().value())
                .phone(customer.phone().value())
                .document(String.valueOf(customer.document()))
                .promotionNotificationsAllowed(customer.isPromotionNotificationsAllowed())
                .archived(customer.isArchived())
                .registeredAt(customer.registeredAt())
                .archivedAt(customer.archivedAt())
                .loyaltyPoints(customer.loyaltyPoints().value())
                .address(AddressEmbeddable.builder()
                .street(customer.address().getStreet())
                .number(customer.address().getNumber())
                .complement(customer.address().getComplement())
                .neighborhood(customer.address().getNeighborhood())
                .city(customer.address().getCity())
                .state(customer.address().getState())
                .zipCode(customer.address().getZipCode().value()) // ZipCode é VO
                .build()).build();

    }
}
