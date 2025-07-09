// Customer.java
package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

@Getter
@Setter
public class Customer {

    public interface BrandNewCustomerBuild {
        BrandNewCustomerBuild fullName(FullName name);
        BrandNewCustomerBuild birthDate(BirthDate birthDate);
        BrandNewCustomerBuild email(Email email);
        BrandNewCustomerBuild phone(Phone phone);
        BrandNewCustomerBuild document(Document document);
        BrandNewCustomerBuild promotionNotificationsAllowed(boolean allowed);
        BrandNewCustomerBuild address(Address address);
        Customer build();
    }

    public static BrandNewCustomerBuild brandNew() {
        return new BrandNewCustomerBuilder();
    }

    public static Builder existing() {
        return new Builder();
    }

    public static class Builder {
        private Customer customer;

        public Builder() {
            this.customer = new Customer();
        }

        public Builder id(CustomerId id) {
            customer.setId(id);
            return this;
        }

        public Builder fullName(FullName fullName) {
            customer.setFullName(fullName);
            return this;
        }

        public Builder birthDate(BirthDate birthDate) {
            customer.setBirthDate(birthDate);
            return this;
        }

        public Builder email(Email email) {
            customer.setEmail(email);
            return this;
        }

        public Builder phone(Phone phone) {
            customer.setPhone(phone);
            return this;
        }

        public Builder document(Document document) {
            customer.setDocument(document);
            return this;
        }

        public Builder promotionNotificationsAllowed(boolean allowed) {
            customer.setPromotionNotificationsAllowed(allowed);
            return this;
        }

        public Builder archived(boolean archived) {
            customer.setArchived(archived);
            return this;
        }

        public Builder registeredAt(OffsetDateTime registeredAt) {
            customer.setRegisteredAt(registeredAt);
            return this;
        }

        public Builder archivedAt(OffsetDateTime archivedAt) {
            customer.setArchivedAt(archivedAt);
            return this;
        }

        public Builder loyaltyPoints(LoyaltyPoints loyaltyPoints) {
            customer.setLoyaltyPoints(loyaltyPoints);
            return this;
        }

        public Builder address(Address address) {
            customer.setAddress(address);
            return this;
        }

        public Customer build() {
            return customer;
        }
    }

    private static class BrandNewCustomerBuilder implements BrandNewCustomerBuild {
        private final Customer customer = new Customer();

        public BrandNewCustomerBuild fullName(FullName name) {
            customer.setFullName(name);
            return this;
        }

        public BrandNewCustomerBuild birthDate(BirthDate birthDate) {
            customer.setBirthDate(birthDate);
            return this;
        }

        public BrandNewCustomerBuild email(Email email) {
            customer.setEmail(email);
            return this;
        }

        public BrandNewCustomerBuild phone(Phone phone) {
            customer.setPhone(phone);
            return this;
        }

        public BrandNewCustomerBuild document(Document document) {
            customer.setDocument(document);
            return this;
        }

        public BrandNewCustomerBuild promotionNotificationsAllowed(boolean allowed) {
            customer.setPromotionNotificationsAllowed(allowed);
            return this;
        }

        public BrandNewCustomerBuild address(Address address) {
            customer.setAddress(address);
            return this;
        }

        public Customer build() {
            customer.setId(new CustomerId());
            customer.setArchived(false);
            customer.setRegisteredAt(OffsetDateTime.now());
            customer.setArchivedAt(null);
            customer.setLoyaltyPoints(LoyaltyPoints.ZERO);
            return customer;
        }
    }

    private CustomerId id;
    private FullName fullName;
    private BirthDate birthDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltyPoints loyaltyPoints;
    private Address address;

    public void addLoyaltyPoints(LoyaltyPoints loyaltyPointsAdded) {
        verifyIfChangeable();
        if (loyaltyPointsAdded == null || loyaltyPointsAdded.value() <= 0) {
            throw new IllegalArgumentException("Loyalty points must be greater than zero");
        }
        this.setLoyaltyPoints(this.loyaltyPoints.add(loyaltyPointsAdded));
    }

    public void archive() {
        verifyIfChangeable();
        this.setArchived(true);
        this.setArchivedAt(OffsetDateTime.now());
        this.setFullName(new FullName("Anonymous", "Anonymous"));
        this.setPhone(new Phone("000-000-0000"));
        this.setDocument(new Document("000-00-0000"));
        this.setEmail(new Email(UUID.randomUUID() + "@anonymous.com"));
        this.setBirthDate(null);
        this.setPromotionNotificationsAllowed(false);
        this.setAddress(new Address(
                "Bourbon Street",
                "Anonymized",
                this.address.getNeighborhood(),
                this.address.city(),
                this.address.getState(),
                this.address.zipCode().value(),
                null
        ));
    }

    public void enablePromotionNotifications() {
        verifyIfChangeable();
        this.setPromotionNotificationsAllowed(true);
    }

    public void disablePromotionNotifications() {
        verifyIfChangeable();
        this.setPromotionNotificationsAllowed(false);
    }

    public void changeName(FullName fullName) {
        verifyIfChangeable();
        this.setFullName(fullName);
    }

    public void changeEmail(Email email) {
        verifyIfChangeable();
        this.setEmail(email);
    }

    public void changePhone(Phone phone) {
        verifyIfChangeable();
        this.setPhone(phone);
    }

    public void changeAddress(Address address) {
        verifyIfChangeable();
        this.setAddress(address);
    }

    public CustomerId id() { return id; }
    public FullName fullName() { return fullName; }
    public BirthDate birthDate() { return birthDate; }
    public Email email() { return email; }
    public Phone phone() { return phone; }
    public Document document() { return document; }
    public Boolean isPromotionNotificationsAllowed() { return promotionNotificationsAllowed; }
    public Boolean isArchived() { return archived; }
    public OffsetDateTime registeredAt() { return registeredAt; }
    public OffsetDateTime archivedAt() { return archivedAt; }
    public LoyaltyPoints loyaltyPoints() { return loyaltyPoints; }
    public Address address() { return address; }

    private void setId(CustomerId id) { this.id = Objects.requireNonNull(id); }
    private void setFullName(FullName fullName) { this.fullName = Objects.requireNonNull(fullName); }
    private void setBirthDate(BirthDate birthDate) { this.birthDate = birthDate; }
    private void setEmail(Email email) { this.email = Objects.requireNonNull(email); }
    private void setPhone(Phone phone) { this.phone = Objects.requireNonNull(phone); }
    private void setDocument(Document document) { this.document = Objects.requireNonNull(document); }
    private void setPromotionNotificationsAllowed(Boolean value) { this.promotionNotificationsAllowed = Objects.requireNonNull(value); }
    private void setArchived(Boolean value) { this.archived = Objects.requireNonNull(value); }
    private void setRegisteredAt(OffsetDateTime value) { this.registeredAt = Objects.requireNonNull(value); }
    private void setArchivedAt(OffsetDateTime value) { this.archivedAt = value; }
    private void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) { this.loyaltyPoints = Objects.requireNonNull(loyaltyPoints); }
    private void setAddress(Address address) { this.address = Objects.requireNonNull(address); }

    private void verifyIfChangeable() {
        if (Boolean.TRUE.equals(this.isArchived())) {
            throw new CustomerArchivedException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
