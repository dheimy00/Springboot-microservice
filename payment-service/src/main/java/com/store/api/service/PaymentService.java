package com.store.api.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.store.api.dto.PaymentDTO;

public interface PaymentService {
	
	List<PaymentDTO> findAll();
	
	PaymentDTO save(PaymentDTO paymentDTO) throws JsonProcessingException;
		
	PaymentDTO findPaymentHistoryByOrderId(Integer orderId) throws JsonProcessingException;

}
