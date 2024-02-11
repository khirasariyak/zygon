package com.zygon.orderService.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zygon.orderService.exception.OrderServiceCustomException;
import com.zygon.orderService.external.response.ErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

import static com.zygon.orderService.common.ExceptionMessageConstants.COMMON_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String s, Response response) {

    log.info("::{}", response.request().url());
    log.info("::{}", response.request().headers());

    try {
      ErrorResponse errorResponse = new ObjectMapper().readValue(response.body().asInputStream(), ErrorResponse.class);
      return new OrderServiceCustomException(errorResponse.getErrorMessage(), errorResponse.getHttpStatus());
    } catch (IOException e) {
      log.error(e);
      throw new OrderServiceCustomException(COMMON_ERROR, INTERNAL_SERVER_ERROR);
    }
  }

}
