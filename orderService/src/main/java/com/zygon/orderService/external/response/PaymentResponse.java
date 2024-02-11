package com.zygon.orderService.external.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
  private long id;
  private String paymentStatus;
  private String paymentMode;
  private long amount;
  private Instant paymentDate;
  private long orderId;
}
