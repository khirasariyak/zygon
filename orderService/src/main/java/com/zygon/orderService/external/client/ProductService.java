package com.zygon.orderService.external.client;

import com.zygon.orderService.exception.OrderServiceCustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "Product-Service/v1/products")
public interface ProductService {

  @PutMapping("/{id}/reduceQuantity")
  public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity);

  default void fallBack(Exception e) {
    throw new OrderServiceCustomException("Product Service is not available!", INTERNAL_SERVER_ERROR);
  }

}
