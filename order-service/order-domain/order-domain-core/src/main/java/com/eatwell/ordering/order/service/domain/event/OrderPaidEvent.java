package com.eatwell.ordering.order.service.domain.event;

import com.eatwell.ordering.order.service.domain.entity.order.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent{


    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
