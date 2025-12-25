package com.algaworks.algashop.ordering.infrastructure.adapters.out.persistence.customer.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.algaworks.algashop.ordering.core.ports.out.customer.ForNotifyingCustomers;
import com.algaworks.algashop.ordering.core.ports.out.customer.ForNotifyingCustomers.NotifyNewRegistrationInput;

@Service
@Slf4j
public class CustomerNotificationApplicationServiceFakeImpl implements ForNotifyingCustomers {

    @Override
    public void notifyNewRegistration(NotifyNewRegistrationInput input) {
        log.info("Welcome {}", input.firstName());
        log.info("User your email to access your account {}", input.email());
    }
}
