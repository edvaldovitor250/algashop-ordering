package com.algaworks.algashop.ordering.application.customer.notification;

import java.util.UUID;

public interface CustomerNotificationService {
    void notifyCustomerRegistration(UUID customerId);

}
