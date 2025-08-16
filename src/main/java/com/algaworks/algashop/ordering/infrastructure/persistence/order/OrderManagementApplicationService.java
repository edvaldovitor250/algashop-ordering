package com.algaworks.algashop.ordering.infrastructure.persistence.order;

import com.algaworks.algashop.ordering.domain.model.order.OrderStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderManagementApplicationService {

    private final OrderPersistenceEntityRepository orderRepository;

    public void cancel(Long rawOrderId) {

        Optional<OrderPersistenceEntity> byId = orderRepository.findById(rawOrderId);
        byId.ifPresent(order -> {
            order.setStatus(String.valueOf(OrderStatus.CANCELED));
            orderRepository.save(order);
        });

    }

    public void markAsPaid(Long rawOrderId) {

        Optional<OrderPersistenceEntity> byId = orderRepository.findById(rawOrderId);
        byId.ifPresent(order -> {
            order.setStatus(String.valueOf(OrderStatus.PAID));
            orderRepository.save(order);
        });

    }

    public void marksAsReady(Long rawOrderId) {

        Optional<OrderPersistenceEntity> byId = orderRepository.findById(rawOrderId);
        byId.ifPresent(order -> {
            order.setStatus(String.valueOf(OrderStatus.READY));
            orderRepository.save(order);
        });

    }

}
