package com.eatwell.ordering.order.service.domain.handler;


import com.eatwell.ordering.order.service.domain.dto.create.req.CreateOrderCommand;
import com.eatwell.ordering.order.service.domain.dto.create.resp.CreateOrderResp;
import com.eatwell.ordering.order.service.domain.event.OrderCreatedEvent;
import com.eatwell.ordering.order.service.domain.mapper.OrderDataMapper;
import com.eatwell.ordering.order.service.domain.ports.output.message.pulisher.payment.OrderCreatedPaymentReqMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Component
public class OrderCreateCommandHandler {


    private final OrderDataMapper orderDataMapper;
    private final OrderCreateHelper orderCreateHelper;
    private final OrderCreatedPaymentReqMessagePublisher orderCreatedPaymentReqMessagePublisher;

    public OrderCreateCommandHandler(OrderDataMapper orderDataMapper,
                                     OrderCreateHelper orderCreateHelper,
                                     OrderCreatedPaymentReqMessagePublisher orderCreatedPaymentReqMessagePublisher) {
        this.orderDataMapper = orderDataMapper;
        this.orderCreateHelper = orderCreateHelper;
        this.orderCreatedPaymentReqMessagePublisher = orderCreatedPaymentReqMessagePublisher;
    }


    public CreateOrderResp createOrder(CreateOrderCommand createOrderCommand){
       OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        orderCreatedPaymentReqMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResp(orderCreatedEvent.getOrder());
    }

}
