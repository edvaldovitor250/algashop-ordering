package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.shipping;

import com.algaworks.algashop.ordering.core.application.shipping.ShippingApplicationService;
import com.algaworks.algashop.ordering.core.application.shipping.ShippingCostPreviewInput;
import com.algaworks.algashop.ordering.core.application.shipping.ShippingCostPreviewOutput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.algaworks.algashop.ordering.infrastructure.security.SecurityAnnotations.*;

@RestController
@RequiredArgsConstructor
public class ShippingCostController {

    private final ShippingApplicationService shippingApplicationService;

    @GetMapping("/shipping/cost/preview")
    @CanPreviewShippingCosts
    public ShippingCostPreviewOutput previewCost(@RequestBody @Valid ShippingCostPreviewInput input) {
        return shippingApplicationService.previewCost(input);
    }

}
