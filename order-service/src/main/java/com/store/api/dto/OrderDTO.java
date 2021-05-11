package com.store.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor		
public class OrderDTO {

	private int id;
	
	@NotBlank(message = "Name may not be blank")
	@Size(min=2, max=30)
	private String name;
	
	@NotNull
	private int qty;
	
	@NotNull
	private double price;
	
}
