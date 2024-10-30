package com.eatwell.ordering.order.service.application.rest;

import com.eatwell.ordering.order.service.domain.dto.create.req.CreateOrderCommand;
import com.eatwell.ordering.order.service.domain.dto.create.resp.CreateOrderResp;
import com.eatwell.ordering.order.service.domain.dto.track.req.TrackOrderQuery;
import com.eatwell.ordering.order.service.domain.dto.track.resp.TrackOrderResp;
import com.eatwell.ordering.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResp> createOrder(@RequestBody CreateOrderCommand createOrderCommand){
        log.info("Creating order for customer: {} at restaurant: {}", createOrderCommand.getCustomerId(),
                createOrderCommand.getRestaurantId());
        CreateOrderResp createOrderResp = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order created for customer: {} with tracking id: {}", createOrderCommand.getCustomerId(),
                createOrderResp.getOrderTrackingId());
        return ResponseEntity.ok(createOrderResp);

    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResp> getOrderByTrackingId(UUID trackingId){
        log.info("Retrieving information for order with the tracking id: {}", trackingId);
        TrackOrderResp trackOrderResp =  orderApplicationService.trackOrder(TrackOrderQuery.builder()
                        .orderTrackingId(trackingId)
                .build());
        log.info("Returning order status with the tracking id: {}", trackOrderResp.getOrderTrackingId());
        return ResponseEntity.ok(trackOrderResp);
    }
}
