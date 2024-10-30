package com.eatwell.ordering.order.service.domain.valueobject;

import com.eatwell.ordering.domain.valueobject.BaseId;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}
