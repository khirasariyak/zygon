package com.zygon.productService.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductServiceCustomException extends RuntimeException {

  private HttpStatus httpStatus;

  public ProductServiceCustomException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
