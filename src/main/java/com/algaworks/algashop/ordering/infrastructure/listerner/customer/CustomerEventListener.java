package com.algaworks.algashop.ordering.infrastructure.listerner.customer;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerArchivedEvent;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerEventListener {

    @EventListener
    public void listen(CustomerRegisteredEvent event) {
        log.info("Customer registered: {}", event);
    }

    @EventListener
    public void listen2(CustomerRegisteredEvent event) {
        log.info("Customer registered: {}", event);
    }

    @EventListener
    public void listen2(CustomerArchivedEvent event) {
        log.info("Customer registered: {}", event);
    }




}
