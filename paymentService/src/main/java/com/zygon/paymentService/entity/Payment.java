package com.zygon.paymentService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "PAYMENT_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "ORDER_ID")
  private long orderId;

  @Column(name = "MODE")
  private String paymentMode;

  @Column(name = "REFERENCE_NUMBER")
  private String referenceNumber;

  @Column(name = "PAYMENT_DATE")
  private Instant paymentDate;

  @Column(name = "STATUS")
  private String paymentStatus;

  @Column(name = "AMOUNT")
  private long amount;
}
