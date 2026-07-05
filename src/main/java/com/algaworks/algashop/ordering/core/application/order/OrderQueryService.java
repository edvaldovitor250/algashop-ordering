package com.algaworks.algashop.ordering.core.application.order;

import com.algaworks.algashop.ordering.core.ports.in.order.ForQueryingOrders;
import com.algaworks.algashop.ordering.core.ports.in.order.OrderFilter;
import com.algaworks.algashop.ordering.core.ports.out.order.ForObtainingOrders;
import com.algaworks.algashop.ordering.core.ports.out.order.OrderDetailOutput;
import com.algaworks.algashop.ordering.core.ports.out.order.OrderSummaryOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderQueryService implements ForQueryingOrders {

    private final ForObtainingOrders forObtainingOrders;
    private final SecurityCheckApplicationService securityCheck;

    public OrderDetailOutput findById(String id) {
        if (securityCheck.isCustomer()) {
            OrderDetailOutput order = forObtainingOrders.findById(id);
            if (!order.getCustomerId().equals(securityCheck.getAuthenticatedUserId())) {
                throw new IllegalArgumentException("Customer is not authorized to access this order");
            }
        }
        return forObtainingOrders.findById(id);
    }

    public Page<OrderSummaryOutput> filter(OrderFilter filter) {
        if (securityCheck.isCustomer()) {
            filter.setCustomerId(securityCheck.getAuthenticatedUserId());
        }
        return forObtainingOrders.filter(filter);
    }

    private boolean canAccessOrder(OrderDetailOutput order) {
        if (securityCheck.isCustomer()) {
            return order.getCustomerId().equals(securityCheck.getAuthenticatedUserId());
        }
        return true;
    }
}
