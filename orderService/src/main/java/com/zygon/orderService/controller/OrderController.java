package com.zygon.orderService.controller;

import com.zygon.orderService.model.OrderRequest;
import com.zygon.orderService.model.OrderResponse;
import com.zygon.orderService.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
@Log4j2
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping
  public ResponseEntity<Long> addOrder(@RequestBody OrderRequest orderRequest) {
    long orderId = orderService.addOrder(orderRequest);
    return new ResponseEntity<>(orderId, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderResponse> getOrderById(@PathVariable("id") long id) {
    return new ResponseEntity<>(
      orderService.getOrderById(id),
      HttpStatus.OK
    );
  }

}
