package com.algaworks.algashop.ordering.application.order.query;

import com.algaworks.algashop.ordering.application.utility.PageFilter;
import org.hibernate.query.Page;

public interface OrderQueryService {

    OrderDetailOutput findById(String orderId);
    Page<OrderSummaryOutput> filter(PageFilter filter);
}
