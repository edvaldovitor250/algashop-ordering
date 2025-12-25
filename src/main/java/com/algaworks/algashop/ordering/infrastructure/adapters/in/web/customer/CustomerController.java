package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.customer;

import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ShoppingCartOutput;
import com.algaworks.algashop.ordering.core.ports.in.customer.CustomerFilter;
import com.algaworks.algashop.ordering.core.ports.in.customer.CustomerInput;
import com.algaworks.algashop.ordering.core.ports.in.customer.ForManagingCustomers;
import com.algaworks.algashop.ordering.core.ports.in.customer.CustomerOutput;
import com.algaworks.algashop.ordering.core.ports.in.customer.ForQueryingCustomers;
import com.algaworks.algashop.ordering.core.ports.in.customer.CustomerSummaryOutput;
import com.algaworks.algashop.ordering.core.ports.in.customer.CustomerUpdateInput;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.algaworks.algashop.ordering.presentation.PageModel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ForManagingCustomers customerManagementApplicationService;
    private final ForQueryingCustomers customerQueryService;
    private final ForQueryingShoppingCarts shoppingCartQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerOutput create(@RequestBody @Valid CustomerInput input, HttpServletResponse httpServletResponse) {
        UUID customerId = customerManagementApplicationService.create(input);

        UriComponentsBuilder builder = fromMethodCall(on(CustomerController.class).findById(customerId));
        httpServletResponse.addHeader("Location", builder.toUriString());

        return customerQueryService.findById(customerId);
    }

    @GetMapping
    public PageModel<CustomerSummaryOutput> findAll(CustomerFilter customerFilter) {
        return PageModel.of(customerQueryService.filter(customerFilter));
    }

    @GetMapping("/{customerId}")
    public CustomerOutput findById(@PathVariable UUID customerId) {
        return customerQueryService.findById(customerId);
    }

    @GetMapping("/{customerId}/shopping-cart")
    public ShoppingCartOutput findShoppingCartByCustomerId(@PathVariable UUID customerId) {
        return shoppingCartQueryService.findByCustomerId(customerId);
    }

    @PutMapping("/{customerId}")
    public CustomerOutput update(@PathVariable UUID customerId,
                                 @RequestBody @Valid CustomerUpdateInput input) {
        customerManagementApplicationService.update(customerId, input);
        return customerQueryService.findById(customerId);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID customerId) {
        customerManagementApplicationService.archive(customerId);
    }

}
