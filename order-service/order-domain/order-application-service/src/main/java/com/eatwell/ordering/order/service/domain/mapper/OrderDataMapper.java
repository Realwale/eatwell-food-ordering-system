package com.eatwell.ordering.order.service.domain.mapper;


import com.eatwell.ordering.domain.valueobject.*;
import com.eatwell.ordering.order.service.domain.dto.create.req.CreateOrderCommand;
import com.eatwell.ordering.order.service.domain.dto.create.req.OrderAddress;
import com.eatwell.ordering.order.service.domain.dto.create.resp.CreateOrderResp;
import com.eatwell.ordering.order.service.domain.entity.Product;
import com.eatwell.ordering.order.service.domain.entity.StreetAddress;
import com.eatwell.ordering.order.service.domain.entity.order.Order;
import com.eatwell.ordering.order.service.domain.entity.order.OrderItem;
import com.eatwell.ordering.order.service.domain.entity.restaurant.Restaurant;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand){
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItem().stream()
                        .map(orderItem -> new Product(new ProductId(orderItem.getProductId())))
                .collect(Collectors.toList()))
                .build();
    }



    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand){
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemToOrderItemEntities(createOrderCommand.getItem()))
                .build();
    }

    private List<OrderItem> orderItemToOrderItemEntities(@NotNull List<com.eatwell.ordering.order.service.domain.dto.create.req.OrderItem> item) {
        return item.stream()
                .map(orderItem ->
                    OrderItem.builder()
                            .product(new Product(new ProductId(orderItem.getProductId())))
                            .price(new Money(orderItem.getPrice()))
                            .quantity(orderItem.getQuantity())
                            .subTotal(new Money(orderItem.getSubTotal()))
                            .build()
                ).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(@NotNull OrderAddress address) {
        return new StreetAddress(
                UUID.randomUUID(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode()
        );
    }

    public CreateOrderResp orderToCreateOrderResp(Order order){
        return CreateOrderResp.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }
}
