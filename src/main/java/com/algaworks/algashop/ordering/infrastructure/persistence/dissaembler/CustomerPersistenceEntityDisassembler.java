package com.algaworks.algashop.ordering.infrastructure.persistence.dissaembler;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;

public class CustomerPersistenceEntityDisassembler {

    public Customer toDomain(CustomerPersistenceEntity entity) {
        return Customer.existing()
                .id(new CustomerId(entity.getId()))
                .fullName(new FullName(entity.getFirstName(), entity.getLastName()))
                .birthDate(null)
                .email(new Email(entity.getEmail()))
                .phone(new Phone(entity.getPhone()))
                .document(new Document(entity.getDocument()))
                .promotionNotificationsAllowed(entity.getPromotionNotificationsAllowed())
                .archived(entity.getArchived())
                .registeredAt(entity.getRegisteredAt())
                .archivedAt(entity.getArchivedAt())
                .loyaltyPoints(new LoyaltyPoints(entity.getLoyaltyPoints()))
                .address(new Address(
                        entity.getAddress().getStreet(),
                        entity.getAddress().getNumber(),
                        entity.getAddress().getNeighborhood(),
                        entity.getAddress().getCity(),
                        entity.getAddress().getState(),
                        new ZipCode(entity.getAddress().getZipCode()),
                        entity.getAddress().getComplement())).build();

    }
}
