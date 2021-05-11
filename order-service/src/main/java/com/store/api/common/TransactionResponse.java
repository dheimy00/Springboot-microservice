package com.store.api.common;

import com.store.api.dto.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
	
	private OrderDTO orderDTO;
	
	private Double amont;
	
	private String transactionId;
	
	private String message;
	
	

}
