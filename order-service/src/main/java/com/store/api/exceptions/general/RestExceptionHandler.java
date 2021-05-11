package com.store.api.exceptions.general;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ResourceNotFoundDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
			HttpServletRequest request) {

		return new ResponseEntity<>(ResourceNotFoundDetails.builder()
				.timeStamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value())
				.title("Resource Not Found")
				.detail(exception.getMessage())
				.developerMessage(exception.getClass().getName())
				.build(), null, HttpStatus.NOT_FOUND);
	}
		
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
		.timeStamp(LocalDateTime.now())
		.status(HttpStatus.NOT_FOUND.value())
		.title(exception.getCause().getMessage())
		.detail(exception.getMessage())
		.developerMessage(exception.getClass().getName())
		.build();
		return new ResponseEntity<>(exceptionDetails, headers, status);
	}

	


}
