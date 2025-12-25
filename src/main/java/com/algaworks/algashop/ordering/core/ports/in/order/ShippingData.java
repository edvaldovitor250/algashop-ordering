package com.algaworks.algashop.ordering.core.ports.in.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.algaworks.algashop.ordering.core.ports.commons.AddressData;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingData {
    private BigDecimal cost;
    private LocalDate expectedDate;
    private RecipientData recipient;
    private AddressData address;
}

