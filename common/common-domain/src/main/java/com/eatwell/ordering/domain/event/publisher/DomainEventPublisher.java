package com.eatwell.ordering.domain.event.publisher;

import com.eatwell.ordering.domain.event.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent>{

    void publish(T domainEvent);
}
