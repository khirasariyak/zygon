package com.zygon.orderService.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderServiceCustomException extends RuntimeException {

  private HttpStatus httpStatus;

  public OrderServiceCustomException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
