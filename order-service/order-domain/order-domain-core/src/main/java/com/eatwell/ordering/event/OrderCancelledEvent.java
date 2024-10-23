package com.eatwell.ordering.event;

import com.eatwell.ordering.entity.order.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends OrderEvent{

    public OrderCancelledEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
