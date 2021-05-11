package com.store.api.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.store.api.common.TransactionRequest;
import com.store.api.common.TransactionResponse;
import com.store.api.dto.OrderDTO;

public interface OrderService {
	
	List<OrderDTO> findAll();
	
	TransactionResponse save(TransactionRequest request) throws JsonProcessingException;
	
	OrderDTO findById(Integer id);
	
	OrderDTO update(Integer id,OrderDTO orderDTO);
	
	void delete(Integer id);

}
