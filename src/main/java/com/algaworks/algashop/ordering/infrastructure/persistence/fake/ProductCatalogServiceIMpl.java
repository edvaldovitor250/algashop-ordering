package com.algaworks.algashop.ordering.infrastructure.persistence.fake;

import com.algaworks.algashop.ordering.domain.model.service.ProductCatalogService;
import com.algaworks.algashop.ordering.domain.model.valueobject.Product;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.ProductId;

import java.util.Optional;

public class ProductCatalogServiceImpl implements ProductCatalogService {

    @Override
    public Optional<Product> ofId(ProductId productId) {
        Product product = Product.builder()
                .id(productId)
                .name("Sample Product")
                .price(100.00)
                .build();
        return Optional.of(product);
    }
}
