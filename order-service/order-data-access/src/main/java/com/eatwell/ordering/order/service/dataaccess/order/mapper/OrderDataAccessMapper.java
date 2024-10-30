package com.eatwell.ordering.order.service.dataaccess.order.mapper;

import com.eatwell.ordering.domain.valueobject.*;
import com.eatwell.ordering.order.service.dataaccess.order.entity.OrderAddressEntity;
import com.eatwell.ordering.order.service.dataaccess.order.entity.OrderEntity;
import com.eatwell.ordering.order.service.dataaccess.order.entity.OrderItemEntity;
import com.eatwell.ordering.order.service.domain.entity.Product;
import com.eatwell.ordering.order.service.domain.entity.StreetAddress;
import com.eatwell.ordering.order.service.domain.entity.order.Order;
import com.eatwell.ordering.order.service.domain.entity.order.OrderItem;
import com.eatwell.ordering.order.service.domain.valueobject.OrderItemId;
import com.eatwell.ordering.order.service.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.eatwell.ordering.order.service.domain.entity.order.Order.FAILURE_MESSAGE_DELIMITER;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order){
        OrderEntity orderEntity = OrderEntity.builder()
                .id(order.getId().getValue())
                .customerId(order.getCustomerId().getValue())
                .restaurantId(order.getRestaurantId().getValue())
                .trackingId(order.getTrackingId().getValue())
                .orderAddressEntity(deliveryAddressToAddressEntity(order.getDeliveryAddress()))
                .orderItems(orderItemsToOrderItemEntities(order.getItems()))
                .price(order.getPrice().getAmount())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages() != null ? String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessages()) : "")
                .build();

        orderEntity.getOrderAddressEntity().setOrder(orderEntity);
        orderEntity.getOrderItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        return orderEntity;
    }


    public Order orderEntityToOrder(OrderEntity orderEntity){
        return Order.builder()
                .orderId(new OrderId(orderEntity.getId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .restaurantId(new RestaurantId(orderEntity.getRestaurantId()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .price(new Money(orderEntity.getPrice()) )
                .items(orderItemEntitiesToOrderItems(orderEntity.getOrderItems()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages().split(FAILURE_MESSAGE_DELIMITER))))
                .build();
    }


    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> items) {
        return items.stream()
                .map(orderItemEntity -> OrderItem.builder()
                        .orderItemId(new OrderItemId(orderItemEntity.getId()))
                        .product(new Product(new ProductId(orderItemEntity.getProductId())))
                        .price(new Money(orderItemEntity.getPrice()))
                        .subTotal(new Money(orderItemEntity.getSubTotal()))
                        .quantity(orderItemEntity.getQuantity())
                        .build())
                .collect(Collectors.toList());
    }

    private StreetAddress addressEntityToDeliveryAddress(OrderAddressEntity address) {
        return new StreetAddress(address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getPostalCode());
    }

    private List<OrderItemEntity> orderItemsToOrderItemEntities(List<OrderItem> items) {
        return items.stream().map(
                orderItem -> OrderItemEntity.builder()
                        .id(orderItem.getId().getValue())
                        .productId(orderItem.getProduct().getId().getValue())
                        .price(orderItem.getPrice().getAmount())
                        .quantity(orderItem.getQuantity())
                        .subTotal(orderItem.getSubTotal().getAmount())
                        .build())
                .collect(Collectors.toList());
    }

    private OrderAddressEntity deliveryAddressToAddressEntity(StreetAddress deliveryAddress) {
            return OrderAddressEntity.builder()
                    .id(deliveryAddress.getId())
                    .street(deliveryAddress.getStreet())
                    .city(deliveryAddress.getCity())
                    .postalCode(deliveryAddress.getPostalCode())
                    .build();
    }

}
