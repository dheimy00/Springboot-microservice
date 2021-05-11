package com.store.api.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentDTO {

	private Integer paymentId;
	private String paymentStatus;
	private String transactionId;
	private Integer orderId;
	private Double amont;
}
