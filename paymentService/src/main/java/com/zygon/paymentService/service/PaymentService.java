package com.zygon.paymentService.service;

import com.zygon.paymentService.model.PaymentRequest;
import com.zygon.paymentService.model.PaymentResponse;

public interface PaymentService {
  long doPayment(PaymentRequest paymentRequest);

  PaymentResponse getPaymentByOrderId(long orderId);
}
