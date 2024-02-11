package com.zygon.paymentService.controller;

import com.zygon.paymentService.model.PaymentRequest;
import com.zygon.paymentService.model.PaymentResponse;
import com.zygon.paymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

  @Autowired
  private PaymentService paymentService;

  @PostMapping
  public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest) {
    return new ResponseEntity<>(
      paymentService.doPayment(paymentRequest),
      HttpStatus.OK
    );
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable("orderId") long orderId) {
    return new ResponseEntity<>(
      paymentService.getPaymentByOrderId(orderId),
      HttpStatus.OK
    );
  }

}
