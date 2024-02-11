package com.zygon.paymentService.service;

import com.zygon.paymentService.entity.Payment;
import com.zygon.paymentService.exception.PaymentServiceCustomException;
import com.zygon.paymentService.model.PaymentRequest;
import com.zygon.paymentService.model.PaymentResponse;
import com.zygon.paymentService.repository.PaymentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.zygon.paymentService.common.ExceptionMessageConstants.PAYMENT_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

  @Autowired
  private PaymentRepository paymentRepository;

  @Override
  public long doPayment(PaymentRequest paymentRequest) {
    log.info("Get an request to do the payment. paymentRequest={}", paymentRequest);

    Payment payment = Payment.builder()
      .orderId(paymentRequest.getOrderId())
      .paymentMode(paymentRequest.getPaymentMode().name())
      .amount(paymentRequest.getAmount())
      .referenceNumber(paymentRequest.getReferenceNumber())
      .paymentDate(Instant.now())
      .paymentStatus("SUCCESS")
      .build();

    payment = paymentRepository.save(payment);
    long transactionId = payment.getId();
    log.info("Transaction Completed. transactionId={}", transactionId);
    return transactionId;
  }

  @Override
  public PaymentResponse getPaymentByOrderId(long orderId) {

    log.info("Getting a payment details for the orderId={}", orderId);

    Payment payment = paymentRepository.findByOrderId(orderId)
      .orElseThrow(() ->
         new PaymentServiceCustomException(
          PAYMENT_NOT_FOUND,
          NOT_FOUND
        )
      );

    return PaymentResponse.builder()
      .id(payment.getId())
      .paymentStatus(payment.getPaymentStatus())
      .paymentMode(payment.getPaymentMode())
      .amount(payment.getAmount())
      .paymentDate(payment.getPaymentDate())
      .orderId(payment.getOrderId())
      .build();
  }
}
