package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.product.Product;

import java.util.Optional;
import java.util.UUID;

public interface UnifiedShoppingRepository {

    Optional<ShoppingCart> findCartById(UUID cartId);
    void saveCart(ShoppingCart cart);        // upsert
    void deleteCart(UUID cartId);

    Optional<Product> findProductById(UUID productId);

    ShoppingCart startShopping(UUID customerId);
}
