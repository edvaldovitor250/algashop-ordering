package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPersistenceEntity {

    @Id
    private UUID id;

    @Version
    private Long version;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;
    private String document;

    private Boolean promotionNotificationsAllowed;
    private Boolean archived;

    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;

    private Integer loyaltyPoints;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street")),
            @AttributeOverride(name = "number", column = @Column(name = "address_number")),
            @AttributeOverride(name = "complement", column = @Column(name = "address_complement")),
            @AttributeOverride(name = "district", column = @Column(name = "address_district")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "state", column = @Column(name = "address_state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "address_zip_code"))
    })
    private AddressEmbeddable address;

    @CreatedBy
    private UUID createdByUserId;

    @LastModifiedBy
    private UUID lastModifiedByUserId;

    @LastModifiedDate
    private OffsetDateTime lastModifiedAt;
}
