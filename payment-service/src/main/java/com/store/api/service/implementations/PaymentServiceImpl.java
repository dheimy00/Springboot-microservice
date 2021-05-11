package com.store.api.service.implementations;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.api.dto.PaymentDTO;
import com.store.api.entity.Payment;
import com.store.api.exceptions.general.ResourceNotFoundException;
import com.store.api.repositroy.PaymentRepository;
import com.store.api.service.PaymentService;
import com.store.api.utils.ObjectMapperUtils;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private PaymentRepository orderRepository;
	
	private Logger logger = LoggerFactory.getLogger(PaymentService.class);
	
	@Autowired
	public PaymentServiceImpl(PaymentRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<PaymentDTO> findAll() {
		List<Payment> payments = orderRepository.findAll();
		return ObjectMapperUtils.mapAll(payments, PaymentDTO.class);
	}

	@Override
	public PaymentDTO save(PaymentDTO paymentDTO) throws JsonProcessingException {
		
		paymentDTO.setTransactionId(UUID.randomUUID().toString());
		paymentDTO.setPaymentStatus(paymentProcessing());
		
		logger.info("Payment-Service Request : {}",new ObjectMapper().writeValueAsString(paymentDTO));
		
		Payment payment = orderRepository.save(ObjectMapperUtils.map(paymentDTO, Payment.class));		
		return  ObjectMapperUtils.map(payment, PaymentDTO.class);
	}
	
	public String paymentProcessing() {
		return new Random().nextBoolean() ? "success" : "false";
	}


	@Override
	public PaymentDTO findPaymentHistoryByOrderId(Integer orderId) throws JsonProcessingException {
		Payment payment = orderRepository.findByOrderId(orderId);
		if(payment == null) { throw new ResourceNotFoundException("Order id not found");  }
		
		logger.info("Payment-Service findPaymentHistoryByOrderId : {}",new ObjectMapper().writeValueAsString(payment));
		
		return ObjectMapperUtils.map(payment, PaymentDTO.class);
	}



}
