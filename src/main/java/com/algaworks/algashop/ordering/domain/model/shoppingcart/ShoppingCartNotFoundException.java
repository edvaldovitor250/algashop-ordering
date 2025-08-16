package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import java.util.UUID;

public class ShoppingCartNotFoundException extends RuntimeException {
    public ShoppingCartNotFoundException(UUID cartId) {
        super("ShoppingCart não encontrado: " + cartId);
    }
}
