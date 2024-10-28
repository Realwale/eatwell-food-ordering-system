package com.eatwell.ordering.order.service.domain.ports.input.message.listener.payment;

import com.eatwell.ordering.order.service.domain.dto.message.PaymentResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Validated
@Service
public class PaymentRespMessageListenerImpl implements PaymentRespMessageListener{

    @Override
    public void paymentCompleted(PaymentResp paymentResp) {

    }

    @Override
    public void paymentCancelled(PaymentResp paymentResp) {

    }
}
