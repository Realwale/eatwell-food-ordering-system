package com.eatwell.ordering.order.service.domain.ports.input.message.listener.restaurant;

import com.eatwell.ordering.order.service.domain.dto.message.RestaurantApprovalResp;

public interface RestaurantApprovalRespMessageListener {

    void orderApproved(RestaurantApprovalResp restaurantApprovalResp);

    void orderRejected(RestaurantApprovalResp restaurantApprovalResp);
}
