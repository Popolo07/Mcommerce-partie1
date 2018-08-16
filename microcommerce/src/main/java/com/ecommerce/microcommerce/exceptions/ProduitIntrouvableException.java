package com.ecommerce.microcommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProduitIntrouvableException extends Exception {

	public ProduitIntrouvableException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
