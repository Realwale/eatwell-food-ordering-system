package com.eatwell.ordering.event;

import com.eatwell.ordering.entity.order.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {


    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
