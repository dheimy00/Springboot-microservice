package com.store.api.service.implementations;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.store.api.common.PaymentDTO;
import com.store.api.common.TransactionRequest;
import com.store.api.common.TransactionResponse;
import com.store.api.dto.OrderDTO;
import com.store.api.entity.Order;
import com.store.api.exceptions.general.ResourceNotFoundException;
import com.store.api.repositroy.OrderRepository;
import com.store.api.service.OrderService;
import com.store.api.utils.ObjectMapperUtils;

@Service
@RefreshScope
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	@Lazy
	private RestTemplate restTemplate;
	
	@Value("${microservice.payment-service.endpoints.endpoint.uri}")
	private String ENPOINT_URL;
	
	private Logger logger = LoggerFactory.getLogger(OrderService.class);

	public OrderServiceImpl(OrderRepository orderRepository,RestTemplate restTemplate) {
		this.orderRepository = orderRepository;
		this.restTemplate = restTemplate;
	}

	@Override
	public List<OrderDTO> findAll() {
		List<Order> orders = orderRepository.findAll();
		return ObjectMapperUtils.mapAll(orders, OrderDTO.class);
	}

	@Override
	public TransactionResponse save(TransactionRequest request) throws JsonProcessingException {
		String response = "";
		
		OrderDTO orderDTO = request.getOrderDTO();
		
		Order order = orderRepository.save(ObjectMapperUtils.map(orderDTO, Order.class));
		
		orderDTO = ObjectMapperUtils.map(order, OrderDTO.class);
		
		PaymentDTO payment =  request.getPaymentDTO();
		payment.setOrderId(orderDTO.getId());
		payment.setAmont(orderDTO.getPrice());
		
		logger.info("OrderService request: {}",new ObjectMapper().writeValueAsString(request));
		
		//rest all
		PaymentDTO paymentResponse = restTemplate.postForObject(ENPOINT_URL, payment, PaymentDTO.class);
		response = paymentResponse.getPaymentStatus().equals("success") ? "Payment processing successful and order placed" :
			"There is a failure in payment api, order added to cart";
		 
		logger.info("Order Service getting Response from Payment-Service : "+new ObjectMapper().writeValueAsString(response));
		 
		return new TransactionResponse(orderDTO,paymentResponse.getAmont(),paymentResponse.getTransactionId(),response);
	}

	@Override
	public OrderDTO findById(Integer id) {
		Optional<Order> order = orderRepository.findById(id);
		if(order.isEmpty()) { throw new ResourceNotFoundException("Order not found");  }
		return  ObjectMapperUtils.map(order, OrderDTO.class);
	}
	
	@Override
	public OrderDTO update(Integer id, OrderDTO orderDTO) {
		Order order = orderRepository.findById(id).get();
		if(order == null) { throw new ResourceNotFoundException("Order not found");  }
		order.setName(orderDTO.getName());
		order.setPrice(orderDTO.getPrice());
		order.setQty(orderDTO.getQty());
		return ObjectMapperUtils.map(orderRepository.save(order), OrderDTO.class);
	}

	@Override
	public void delete(Integer id) {
		Optional<Order> order = orderRepository.findById(id);
		if(order.isEmpty()) { throw new ResourceNotFoundException("Order not found");  }
		orderRepository.deleteById(id);
	}



}
