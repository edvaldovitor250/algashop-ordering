package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.commons.model.AddressData;
import com.algaworks.algashop.ordering.application.utility.Mapper;
import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.customer.*;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerManagementApplicationService {

    private final CustomerRegistrationService customerRegistration;
    private final Customers customers;

    private final Mapper mapper;

    @Transactional
    public UUID create(CustomerInput input) {
        Objects.requireNonNull(input);
        AddressData address = input.getAddress();

        Customer customer = customerRegistration.register(
                new FullName(input.getFirstName(), input.getLastName()),
                new BirthDate(input.getBirthDate()),
                new Email(input.getEmail()),
                new Phone(input.getPhone()),
                new Document(input.getDocument()),
                input.getPromotionNotificationsAllowed(),
                Address.builder()
                        .zipCode(new ZipCode(address.getZipCode()))
                        .state(address.getState())
                        .city(address.getCity())
                        .neighborhood(address.getNeighborhood())
                        .street(address.getStreet())
                        .number(address.getNumber())
                        .complement(address.getComplement())
                        .build()
        );

        customers.add(customer);

        return customer.id().value();
    }

    public void addLoyaltyPoints(UUID rawCustomerId, String rawOrderId){
        Objects.requireNonNull(rawCustomerId);
        Objects.requireNonNull(rawOrderId);

        Customer customer = customers.ofId(new CustomerId(rawCustomerId))
                .orElseThrow(() -> new CustomerNotFoundException());

        OrderId orderId = new OrderId(rawOrderId);
        customer.addLoyaltyPoints(orderId);

        customers.add(customer);
    }


    @Transactional(readOnly = true)
    public CustomerOutput findById(UUID customerId) {
        Objects.requireNonNull(customerId);
        Customer customer = customers.ofId(new CustomerId(customerId))
                .orElseThrow(() -> new CustomerNotFoundException());

        return mapper.convert(customer, CustomerOutput.class);
    }

    public void archive(UUID rawCustomerId) {
        Objects.requireNonNull(rawCustomerId);

        Customer customer = customers.ofId(new CustomerId(rawCustomerId))
                .orElseThrow(() -> new CustomerNotFoundException());

        customer.archive();
        customers.add(customer);
    }

    public void changeEmail (UUID rawCustomerId, String newEmail) {
        Objects.requireNonNull(rawCustomerId);
        Objects.requireNonNull(newEmail);

        Customer customer = customers.ofId(new CustomerId(rawCustomerId))
                .orElseThrow(() -> new CustomerNotFoundException());

        customer.changeEmail(new Email(newEmail));
        customers.add(customer);
    }



    @Transactional
    public void update(UUID rawCustomerId, CustomerUpdateInput input) {
        Objects.requireNonNull(input);
        Objects.requireNonNull(rawCustomerId);

        Customer customer = customers.ofId(new CustomerId(rawCustomerId))
                .orElseThrow(() -> new CustomerNotFoundException());

        customer.changeName(new FullName(input.getFirstName(), input.getLastName()));
        customer.changePhone(new Phone(input.getPhone()));

        if (Boolean.TRUE.equals(input.getPromotionNotificationsAllowed())) {
            customer.enablePromotionNotifications();
        } else {
            customer.disablePromotionNotifications();
        }

        AddressData address = input.getAddress();

        customer.changeAddress(Address.builder()
                .zipCode(new ZipCode(address.getZipCode()))
                .state(address.getState())
                .city(address.getCity())
                .neighborhood(address.getNeighborhood())
                .street(address.getStreet())
                .number(address.getNumber())
                .complement(address.getComplement())
                .build());

        customers.add(customer);
    }

}