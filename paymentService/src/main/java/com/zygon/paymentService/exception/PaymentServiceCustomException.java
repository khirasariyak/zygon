package com.zygon.paymentService.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentServiceCustomException extends RuntimeException {

  private HttpStatus httpStatus;

  public PaymentServiceCustomException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
