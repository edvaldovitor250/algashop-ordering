package com.algaworks.algashop.ordering.infrastructure.adapters.in.web.shoppingcart;

import com.algaworks.algashop.ordering.core.application.security.SecurityChecks;
import com.algaworks.algashop.ordering.core.domain.model.customer.CustomerNotFoundException;
import com.algaworks.algashop.ordering.core.domain.model.product.ProductNotFoundException;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForManagingShoppingCarts;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ForQueryingShoppingCarts;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ShoppingCartItemInput;
import com.algaworks.algashop.ordering.core.ports.in.shoppingcart.ShoppingCartOutput;
import com.algaworks.algashop.ordering.infrastructure.adapters.in.web.exceptionhandler.UnprocessableEntityException;
import com.algaworks.algashop.ordering.infrastructure.config.security.SecurityAnnotations;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers/me/shopping-cart")
@RequiredArgsConstructor
public class MyShoppingCartController {

    private final ForManagingShoppingCarts forManagingShoppingCarts;
    private final ForQueryingShoppingCarts forQueryingShoppingCarts;
    private final SecurityChecks securityChecks;

    @SecurityAnnotations.CanReadMyShoppingCart
    @GetMapping
    public ShoppingCartOutput get() {
        return forQueryingShoppingCarts.findByCustomerId(securityChecks.getAuthenticatedUserId());
    }

    @SecurityAnnotations.CanWriteMyShoppingCart
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartOutput create() {
        UUID shoppingCartId;
        try {
            shoppingCartId = forManagingShoppingCarts.createNew(securityChecks.getAuthenticatedUserId());
        } catch (CustomerNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
        return forQueryingShoppingCarts.findById(shoppingCartId);
    }

    @SecurityAnnotations.CanReadMyShoppingCart
    @GetMapping("/items")
    public ShoppingCartItemListModel getItems() {
        ShoppingCartOutput cart = forQueryingShoppingCarts.findByCustomerId(securityChecks.getAuthenticatedUserId());
        return new ShoppingCartItemListModel(cart.getItems());
    }

    @SecurityAnnotations.CanWriteMyShoppingCart
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addItem(@RequestBody @Valid ShoppingCartItemInput input) {
        ShoppingCartOutput cart = forQueryingShoppingCarts.findByCustomerId(securityChecks.getAuthenticatedUserId());
        input.setShoppingCartId(cart.getId());
        try {
            forManagingShoppingCarts.addItem(input);
        } catch (ProductNotFoundException e) {
            throw new UnprocessableEntityException(e.getMessage(), e);
        }
    }

    @SecurityAnnotations.CanWriteMyShoppingCart
    @DeleteMapping("/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void empty() {
        ShoppingCartOutput cart = forQueryingShoppingCarts.findByCustomerId(securityChecks.getAuthenticatedUserId());
        forManagingShoppingCarts.empty(cart.getId());
    }

    @SecurityAnnotations.CanWriteMyShoppingCart
    @DeleteMapping("/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@PathVariable UUID itemId) {
        ShoppingCartOutput cart = forQueryingShoppingCarts.findByCustomerId(securityChecks.getAuthenticatedUserId());
        forManagingShoppingCarts.removeItem(cart.getId(), itemId);
    }

}
