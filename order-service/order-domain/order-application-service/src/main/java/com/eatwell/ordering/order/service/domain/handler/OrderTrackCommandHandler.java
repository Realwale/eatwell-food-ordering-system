package com.eatwell.ordering.order.service.domain.handler;


import com.eatwell.ordering.order.service.domain.dto.track.req.TrackOrderQuery;
import com.eatwell.ordering.order.service.domain.dto.track.resp.TrackOrderResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderTrackCommandHandler {

    public TrackOrderResp trackOrder(TrackOrderQuery trackOrderQuery){
        return null;
    }
}
