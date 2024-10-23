package com.eatwell.ordering.service;

import com.eatwell.ordering.entity.order.Order;
import com.eatwell.ordering.entity.restaurant.Restaurant;
import com.eatwell.ordering.event.OrderCancelledEvent;
import com.eatwell.ordering.event.OrderCreatedEvent;
import com.eatwell.ordering.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);

}
