package com.eatwell.ordering.event;

import com.eatwell.ordering.entity.order.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent{


    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
