package com.store.api.common;

import com.store.api.dto.OrderDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRequest {
	

	private OrderDTO orderDTO;
	
	private PaymentDTO paymentDTO;
	

	
	

}
