package com.eatwell.ordering.order.service.domain.ports.input.message.listener.payment;

import com.eatwell.ordering.order.service.domain.dto.message.PaymentResp;

public interface PaymentRespMessageListener {

    void paymentCompleted(PaymentResp paymentResp);

    void paymentCancelled(PaymentResp paymentResp);
}
