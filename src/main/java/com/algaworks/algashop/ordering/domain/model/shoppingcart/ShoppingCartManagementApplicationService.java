package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.product.Product;
import com.algaworks.algashop.ordering.domain.model.product.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class ShoppingCartManagementApplicationService {

    private final UnifiedShoppingRepository repo;

    public ShoppingCartManagementApplicationService(UnifiedShoppingRepository repo) {
        this.repo = repo;
    }

    // addItem(ShoppingCartItemInput input)
    @Transactional
    public void addItem(ShoppingCartItemInput input) {
        Objects.requireNonNull(input, "input é obrigatório");
        UUID cartId = Objects.requireNonNull(input.shoppingCartId(), "shoppingCartId é obrigatório");
        UUID productId = Objects.requireNonNull(input.productId(), "productId é obrigatório");
        int quantity = input.quantity();
        if (quantity <= 0) throw new IllegalArgumentException("quantity deve ser > 0");

        ShoppingCart cart = repo.findCartById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException(cartId));

        Product product = repo.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        cart.addItem(product, quantity);
        repo.saveCart(cart);
    }

    @Transactional
    public UUID createNew(UUID rawCustomerId) {
        UUID customerId = Objects.requireNonNull(rawCustomerId, "customerId é obrigatório");
        ShoppingCart newCart = repo.startShopping(customerId);
        repo.saveCart(newCart);
        return newCart.getId();
    }

    @Transactional
    public void removeItem(UUID rawShoppingCartId, UUID rawShoppingCartItemId) {
        UUID cartId = Objects.requireNonNull(rawShoppingCartId, "shoppingCartId é obrigatório");
        UUID itemId = Objects.requireNonNull(rawShoppingCartItemId, "shoppingCartItemId é obrigatório");

        ShoppingCart cart = repo.findCartById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException(cartId));

        cart.removeItem(itemId);
        repo.saveCart(cart);
    }

    @Transactional
    public void empty(UUID rawShoppingCartId) {
        UUID cartId = Objects.requireNonNull(rawShoppingCartId, "shoppingCartId é obrigatório");

        ShoppingCart cart = repo.findCartById(cartId)
                .orElseThrow(() -> new ShoppingCartNotFoundException(cartId));

        cart.empty();
        repo.saveCart(cart);
    }

    @Transactional
    public void delete(UUID rawShoppingCartId) {
        UUID cartId = Objects.requireNonNull(rawShoppingCartId, "shoppingCartId é obrigatório");

        // garantir semântica de erro se não existir
        repo.findCartById(cartId).orElseThrow(() -> new ShoppingCartNotFoundException(cartId));
        repo.deleteCart(cartId);
    }
}
