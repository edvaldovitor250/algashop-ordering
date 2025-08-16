package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import java.util.UUID;

public record ShoppingCartItemInput(
        UUID shoppingCartId,
        UUID productId,
        int quantity
) { }
