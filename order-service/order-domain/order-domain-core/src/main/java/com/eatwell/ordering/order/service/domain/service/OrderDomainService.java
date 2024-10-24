package com.eatwell.ordering.order.service.domain.service;

import com.eatwell.ordering.order.service.domain.entity.order.Order;
import com.eatwell.ordering.order.service.domain.entity.restaurant.Restaurant;
import com.eatwell.ordering.order.service.domain.event.OrderCancelledEvent;
import com.eatwell.ordering.order.service.domain.event.OrderCreatedEvent;
import com.eatwell.ordering.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitOrder(Order order, Restaurant restaurant);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);

}
