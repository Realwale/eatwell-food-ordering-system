package com.eatwell.ordering.order.service.domain.handler;


import com.eatwell.ordering.order.service.domain.dto.create.req.CreateOrderCommand;
import com.eatwell.ordering.order.service.domain.entity.customer.Customer;
import com.eatwell.ordering.order.service.domain.entity.order.Order;
import com.eatwell.ordering.order.service.domain.entity.restaurant.Restaurant;
import com.eatwell.ordering.order.service.domain.event.OrderCreatedEvent;
import com.eatwell.ordering.order.service.domain.exception.OrderDomainException;
import com.eatwell.ordering.order.service.domain.mapper.OrderDataMapper;
import com.eatwell.ordering.order.service.domain.ports.output.repository.CustomerRepository;
import com.eatwell.ordering.order.service.domain.ports.output.repository.OrderRepository;
import com.eatwell.ordering.order.service.domain.ports.output.repository.RestaurantRepository;
import com.eatwell.ordering.order.service.domain.service.OrderDomainService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             RestaurantRepository restaurantRepository,
                             OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand){
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitOrder(order, restaurant);
        saveOrder(order);
        log.info("Order is created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        return orderCreatedEvent;
    }


    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()){
            log.warn("Could not find restaurant with id: {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find customer with id: " + createOrderCommand.getRestaurantId());
        }
        return optionalRestaurant.get();
    }

    private void checkCustomer(@NotNull UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);

        if (customer.isEmpty()){
            log.warn("Could not find customer with id: {}", customerId);
            throw new OrderDomainException("Could not find customer with id: " + customerId);
        }
    }

    private Order saveOrder(Order order){
        Order orderRes = orderRepository.save(order);
        if (orderRes == null){
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order saved with id: {}", orderRes.getId().getValue());
        return orderRes;
    }
}
