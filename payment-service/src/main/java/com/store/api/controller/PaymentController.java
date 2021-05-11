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
import com.store.api.dto.PaymentDTO;
import com.store.api.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	private PaymentService paymentService;

	@Autowired
	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<PaymentDTO> save(@Valid @RequestBody PaymentDTO paymentDTO) throws JsonProcessingException {

		PaymentDTO paymentSave = paymentService.save(paymentDTO);

		HttpHeaders responseHeaders = new HttpHeaders();
		URI newURI = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(paymentSave.getPaymentId())
				.toUri();
		responseHeaders.setLocation(newURI);

		return new ResponseEntity<>(paymentSave, HttpStatus.CREATED);

	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<PaymentDTO>> findAll() {

		List<PaymentDTO> orders = paymentService.findAll();

		if (orders.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(orders, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<PaymentDTO> findPaymentHistoryByOrderId(@PathVariable Integer orderId) throws JsonProcessingException {
		return new ResponseEntity<>(paymentService.findPaymentHistoryByOrderId(orderId), HttpStatus.OK);
	}

}
