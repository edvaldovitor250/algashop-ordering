package com.algaworks.algashop.ordering.domain.entity;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.valueobject.Address; // Added for Email anonymization in Customer.archive()
import com.algaworks.algashop.ordering.domain.valueobject.BirthDate;
import com.algaworks.algashop.ordering.domain.valueobject.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.Document;
import com.algaworks.algashop.ordering.domain.valueobject.Email;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.valueobject.Phone;
import com.algaworks.algashop.ordering.domain.valueobject.ZipCode;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(()-> new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("invalid"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now(),
                new Address(
                    "Bourbon Street",
                    "Apt. 114",
                    "North Ville",
                    "1134",
                    "York",
                    "South California",
                    new ZipCode("12345")
                )
            ));
    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now(),
                new Address(
                        "Bourbon Street",
                        "Apt. 114",
                        "North Ville",
                        "1134",
                        "York",
                        "South California",
                        new ZipCode("12345")
                )
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.changeEmail(new Email("invalid")));
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now(),
                new Address(
                        "Bourbon Street",
                        "Apt. 114",
                        "North Ville",
                        "1134",
                        "York",
                        "South California",
                        new ZipCode("12345")
                )
        );

        customer.archive();

        Assertions.assertWith(customer,
            c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous","Anonymous")),
            // The email is randomized in the archive method, so we only assert it's not the original
            c -> assertThat(c.email()).isNotEqualTo(new Email("john.doe@gmail.com")),
            c -> assertThat(c.phone()).isEqualTo(new Phone("000-000-0000")),
            c -> assertThat(c.document()).isEqualTo(new Document("000-00-0000")),
            c -> assertThat(c.birthDate()).isNull(),
            c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
            c -> assertThat(c.address()).isEqualTo(
                    // Corrected: Use the canonical constructor for Address to match the Customer.archive() behavior
                    new Address(
                            "Bourbon Street",
                            null, // Complement is null after anonymization
                            "North Ville",
                            "Anonymized", // Number is "Anonymized" after anonymization
                            "York",
                            "South California",
                            new ZipCode("12345")
                    )
            )
        );

    }

    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("Anonymous", "Anonymous"),
                null,
                new Email("anonymous@anonymous.com"),
                new Phone("000-000-0000"),
                new Document("000-00-0000"),
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                new LoyaltyPoints(10),
                new Address(
                        "Bourbon Street",
                        "Apt. 114",
                        "North Ville",
                        "1134",
                        "York",
                        "South California",
                        new ZipCode("12345")
                )
        );

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changeEmail(new Email("email@gmail.com")));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(()-> customer.changePhone(new Phone("123-123-1111")));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::disablePromotionNotifications);
    }

    @Test
    void given_brandNewCustomer_whenAddLoyaltyPoints_shouldSumPoints() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now(),
                new Address(
                        "Bourbon Street",
                        "Apt. 114",
                        "North Ville",
                        "1134",
                        "York",
                        "South California",
                        new ZipCode("12345")
                )
        );

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

        Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brandNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now(),
                new Address(
                        "Bourbon Street",
                        "Apt. 114",
                        "North Ville",
                        "1134",
                        "York",
                        "South California",
                        new ZipCode("12345")
                )
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltyPoints(0)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.addLoyaltyPoints(new LoyaltyPoints(-10)));
    }
}
