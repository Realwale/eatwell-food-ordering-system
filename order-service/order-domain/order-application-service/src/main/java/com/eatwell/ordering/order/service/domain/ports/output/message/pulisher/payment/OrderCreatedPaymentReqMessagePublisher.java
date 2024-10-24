package com.eatwell.ordering.order.service.domain.ports.output.message.pulisher.payment;

import com.eatwell.ordering.domain.event.publisher.DomainEventPublisher;
import com.eatwell.ordering.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentReqMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
