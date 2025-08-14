package com.algaworks.algashop.ordering.infrastructure.rapidex;

import com.algaworks.algashop.ordering.domain.model.service.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.valueobject.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import com.expedit.ms_jurisprudencias.infrastructure.rapidex.DeliveryCostRequest;
import java.time.LocalDate;
import com.expedit.ms_jurisprudencias.infrastructure.rapidex.DeliveryCostResponse;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "algashop.integrations.shipping.provider", havingValue = "RAPIDEX")
public class ShippingCostServiceRapidexImpl implements ShippingCostService {

    private final com.expedit.ms_jurisprudencias.infrastructure.rapidex.RapiDexAPIClient rapiDexAPIClient;

    @Override
    public CalculationResult calculate(CalculationRequest request) {
        DeliveryCostResponse response = rapiDexAPIClient.calculate(
                new DeliveryCostRequest(
                        request.origin().value(),
                        request.destination().value()
                )
        );

        LocalDate expectedDeliveryDate = LocalDate.now().plusDays(response.getEstimatedDaysToDeliver());

        return CalculationResult.builder()
                .cost(new Money(response.getDeliveryCost()))
                .expectedDate(expectedDeliveryDate)
                .build();
    }
}