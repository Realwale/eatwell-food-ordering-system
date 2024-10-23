package com.eatwell.ordering.entity;

import com.eatwell.ordering.domain.entity.BaseEntity;
import com.eatwell.ordering.valueobject.Money;
import com.eatwell.ordering.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {

    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}
