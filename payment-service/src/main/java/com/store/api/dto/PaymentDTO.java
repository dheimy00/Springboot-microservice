package com.store.api.dto;

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
	private Integer amont;
	
}
