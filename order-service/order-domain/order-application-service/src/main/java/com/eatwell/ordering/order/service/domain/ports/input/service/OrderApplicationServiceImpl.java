package com.eatwell.ordering.order.service.domain.ports.input.service;

import com.eatwell.ordering.order.service.domain.dto.create.req.CreateOrderCommand;
import com.eatwell.ordering.order.service.domain.dto.create.resp.CreateOrderResp;
import com.eatwell.ordering.order.service.domain.dto.track.req.TrackOrderQuery;
import com.eatwell.ordering.order.service.domain.dto.track.resp.TrackOrderResp;
import com.eatwell.ordering.order.service.domain.handler.OrderCreateCommandHandler;
import com.eatwell.ordering.order.service.domain.handler.OrderTrackCommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService{

    private final OrderCreateCommandHandler orderCreateCommandHandler;

    private final OrderTrackCommandHandler orderTrackCommandHandler;

    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }

    @Override
    public CreateOrderResp createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResp trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
