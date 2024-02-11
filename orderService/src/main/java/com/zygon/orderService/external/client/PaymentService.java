package com.zygon.orderService.external.client;

import com.zygon.orderService.exception.OrderServiceCustomException;
import com.zygon.orderService.external.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@CircuitBreaker(name = "external", fallbackMethod = "fallBack")
@FeignClient(name = "Payment-Service/v1/payments")
public interface PaymentService {

  @PostMapping
  public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

  default void fallBack(Exception e) {
    throw new OrderServiceCustomException("Payment Service is not available!", INTERNAL_SERVER_ERROR);
  }

}
