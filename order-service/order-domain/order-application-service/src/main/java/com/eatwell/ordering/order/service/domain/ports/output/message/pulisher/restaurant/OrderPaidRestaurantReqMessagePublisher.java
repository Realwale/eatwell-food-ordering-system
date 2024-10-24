package com.eatwell.ordering.order.service.domain.ports.output.message.pulisher.restaurant;

import com.eatwell.ordering.domain.event.publisher.DomainEventPublisher;
import com.eatwell.ordering.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantReqMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
