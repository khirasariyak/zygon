package com.zygon.orderService.service;

import com.zygon.orderService.entity.Order;
import com.zygon.orderService.exception.OrderServiceCustomException;
import com.zygon.orderService.external.client.PaymentService;
import com.zygon.orderService.external.client.ProductService;
import com.zygon.orderService.external.request.PaymentRequest;
import com.zygon.orderService.external.response.PaymentResponse;
import com.zygon.orderService.external.response.ProductResponse;
import com.zygon.orderService.model.OrderRequest;
import com.zygon.orderService.model.OrderResponse;
import com.zygon.orderService.model.PaymentMode;
import com.zygon.orderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

import static com.zygon.orderService.common.ExceptionMessageConstants.ORDER_NOT_FOUND;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private ProductService productService;

  @Autowired
  private PaymentService paymentService;

  @Autowired
  private RestTemplate restTemplate;

  @Override
  public long addOrder(OrderRequest orderRequest) {
    log.info("Get the request to add an order. orderRequest={}", orderRequest);

    long productId = orderRequest.getProductId();
    long quantity = orderRequest.getQuantity();
    long amount = orderRequest.getAmount();
    PaymentMode paymentMode = orderRequest.getPaymentMode();

    productService.reduceQuantity(productId, quantity);

    log.info("Creating an order with 'CREATED' status.");
    Order order = Order.builder()
      .orderStatus("CREATED")
      .orderDate(Instant.now())
      .amount(amount)
      .productId(productId)
      .quantity(quantity)
      .build();

    order = orderRepository.save(order);
    long orderId = order.getId();

    log.info("Trying to complete the payment...");
    PaymentRequest paymentRequest = PaymentRequest.builder()
      .orderId(orderId)
      .paymentMode(paymentMode)
      .amount(amount)
      .build();

    try {
      paymentService.doPayment(paymentRequest);
      log.info("Payment successful! Changing the orderStatus to 'PLACED'");
      order.setOrderStatus("PLACED");
    } catch (Exception e) {
      log.error("Error occurred while doing the payment, changing the orderStatus to 'PAYMENT_FAILED'");
      order.setOrderStatus("PAYMENT_FAILED");
    }

    orderRepository.save(order);

    log.info("Order added successfully. orderId={}", orderId);
    return orderId;
  }

  @Override
  public OrderResponse getOrderById(long id) {
    log.info("Getting an oder for the id={}", id);

    Order order = orderRepository.findById(id)
      .orElseThrow(() -> new OrderServiceCustomException(ORDER_NOT_FOUND, HttpStatus.NOT_FOUND));

    long productId = order.getProductId();
    log.info("Invoking an API to fetch the product details for productId={}", productId);
    ProductResponse productResponse = restTemplate.getForObject(
      "http://Product-Service/v1/products/" + productId,
      ProductResponse.class
    );

    long orderId = order.getId();
    log.info("Invoking an API to fetch the payment details for orderId={}", orderId);
    PaymentResponse paymentResponse = restTemplate.getForObject(
      "http://Payment-Service/v1/payments/order/" + orderId,
      PaymentResponse.class
    );

    return OrderResponse.builder()
      .orderId(orderId)
      .amount(order.getAmount())
      .orderStatus(order.getOrderStatus())
      .orderDate(order.getOrderDate())
      .product(productResponse)
      .payment(paymentResponse)
      .build();
  }
}
