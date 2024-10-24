package com.eatwell.ordering.order.service.domain.dto.message;


import com.eatwell.ordering.domain.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResp {

    private String id;
    private String sagaId;
    private String orderId;
    private String customerId;
    private String paymentId;
    private BigDecimal price;
    private Instant createdAt;
    private PaymentStatus paymentStatus;
    private List<String> failureMessages;

}
