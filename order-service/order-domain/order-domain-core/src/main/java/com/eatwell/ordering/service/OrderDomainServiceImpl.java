package com.eatwell.ordering.service;

import com.eatwell.ordering.entity.Product;
import com.eatwell.ordering.entity.order.Order;
import com.eatwell.ordering.entity.restaurant.Restaurant;
import com.eatwell.ordering.event.OrderCancelledEvent;
import com.eatwell.ordering.event.OrderCreatedEvent;
import com.eatwell.ordering.event.OrderPaidEvent;
import com.eatwell.ordering.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{

    private static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order, restaurant);
        order.validateOrder();
        order.initOrder();
        log.info("Order with id: {} is initiated", order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }


    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id: {} is paid", order.getId().getValue());
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id: {} is approved", order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        log.info("Order payment is canceling for Order id: {}", order.getId().getValue());
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
        log.info("Order with id: {} is cancelled", order.getId().getValue());
    }


    private void setOrderProductInformation(Order order, Restaurant restaurant) {

        Map<UUID, Product> restaurantProductMap = new HashMap<>();

        restaurant.getProducts().forEach(product -> restaurantProductMap.put(product.getId().getValue(), product));

        order.getItems().forEach(orderItem -> {
            Product currentProduct = orderItem.getProduct();

            Product restaurantProduct = restaurantProductMap.get(currentProduct.getId().getValue());

            if (restaurantProduct != null) {
                currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(),
                        restaurantProduct.getPrice());
            }
        });
    }


    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()){
            throw new OrderDomainException("Restaurant with id "+ restaurant.getId().getValue()
                    + " is currently not active!");
        }

    }
}