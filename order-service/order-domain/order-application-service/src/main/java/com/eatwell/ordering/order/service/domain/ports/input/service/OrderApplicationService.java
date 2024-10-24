package com.eatwell.ordering.order.service.domain.ports.input.service;

import com.eatwell.ordering.order.service.domain.dto.create.req.CreateOrderCommand;
import com.eatwell.ordering.order.service.domain.dto.create.resp.CreateOrderResp;
import com.eatwell.ordering.order.service.domain.dto.track.req.TrackOrderQuery;
import com.eatwell.ordering.order.service.domain.dto.track.resp.TrackOrderResp;
import jakarta.validation.Valid;

public interface OrderApplicationService {

     CreateOrderResp createOrder(@Valid CreateOrderCommand createOrderCommand);

     TrackOrderResp trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
