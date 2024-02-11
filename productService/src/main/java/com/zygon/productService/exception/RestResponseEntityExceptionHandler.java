package com.zygon.productService.exception;

import com.zygon.productService.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ProductServiceCustomException.class)
  public ResponseEntity<ErrorResponse> productServiceExceptionHandler(ProductServiceCustomException exception) {
    return new ResponseEntity<>(
      ErrorResponse.builder().errorMessage(exception.getMessage()).httpStatus(exception.getHttpStatus()).build(),
      exception.getHttpStatus()
    );
  }

}
