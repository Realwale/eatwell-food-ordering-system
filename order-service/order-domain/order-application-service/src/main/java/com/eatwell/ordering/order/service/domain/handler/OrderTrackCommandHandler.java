package com.eatwell.ordering.order.service.domain.handler;


import com.eatwell.ordering.order.service.domain.dto.track.req.TrackOrderQuery;
import com.eatwell.ordering.order.service.domain.dto.track.resp.TrackOrderResp;
import com.eatwell.ordering.order.service.domain.entity.order.Order;
import com.eatwell.ordering.order.service.domain.exception.OrderNotFoundException;
import com.eatwell.ordering.order.service.domain.mapper.OrderDataMapper;
import com.eatwell.ordering.order.service.domain.ports.output.repository.OrderRepository;
import com.eatwell.ordering.order.service.domain.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    private final OrderDataMapper orderDataMapper;
    private final OrderRepository orderRepository;

    public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public TrackOrderResp trackOrder(TrackOrderQuery trackOrderQuery){
        Optional<Order> order = orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if (order.isEmpty()){
            log.warn("Order not found with tracking id: {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Could not find order with tracking id: "+ trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(order.get());
    }
}
