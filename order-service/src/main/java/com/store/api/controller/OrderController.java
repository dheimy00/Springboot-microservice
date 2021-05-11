package com.store.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.store.api.common.TransactionRequest;
import com.store.api.common.TransactionResponse;
import com.store.api.dto.OrderDTO;
import com.store.api.service.OrderService;
import com.store.api.utils.GeneralMessage;

@RestController
@RequestMapping("/order")
public class OrderController {

	private OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TransactionResponse> save(@Valid @RequestBody TransactionRequest request) throws JsonProcessingException {
		
		TransactionResponse response = orderService.save(request);

		HttpHeaders responseHeaders = new HttpHeaders();
		URI newURI = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(response.getOrderDTO().getId())
				.toUri();
		responseHeaders.setLocation(newURI);

		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<OrderDTO>> findAll() {

		List<OrderDTO> orders = orderService.findAll();

		if (orders.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(orders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrderDTO> find(@PathVariable Integer id) {
		
		OrderDTO order = orderService.findById(id);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<GeneralMessage> delete(@PathVariable Integer id,@RequestBody OrderDTO orderDTO) {
		
		orderService.update(id,orderDTO);
		return new ResponseEntity<>(new GeneralMessage("Order updated successfully"), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<GeneralMessage> delete(@PathVariable Integer id) {
		
		orderService.delete(id);
		return new ResponseEntity<>(new GeneralMessage("Order deleted successfully"), HttpStatus.OK);
	}

}
