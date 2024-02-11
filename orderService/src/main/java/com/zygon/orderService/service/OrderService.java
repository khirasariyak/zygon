package com.zygon.orderService.service;

import com.zygon.orderService.model.OrderRequest;
import com.zygon.orderService.model.OrderResponse;

public interface OrderService {

  long addOrder(OrderRequest orderRequest);

  OrderResponse getOrderById(long id);
}
