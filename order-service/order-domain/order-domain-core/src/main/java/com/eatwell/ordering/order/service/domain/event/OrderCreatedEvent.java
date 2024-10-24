package com.eatwell.ordering.order.service.domain.event;

import com.eatwell.ordering.order.service.domain.entity.order.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent {


    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
