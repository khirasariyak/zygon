package com.zygon.orderService.exception;

import com.zygon.orderService.external.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<ErrorResponse> exceptionHandler(OrderServiceCustomException exception) {
    return new ResponseEntity<>(
      ErrorResponse.builder().errorMessage(exception.getMessage()).httpStatus(exception.getHttpStatus()).build(),
      exception.getHttpStatus()
    );
  }

}
