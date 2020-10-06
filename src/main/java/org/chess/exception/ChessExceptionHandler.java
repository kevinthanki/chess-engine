package org.chess.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ChessExceptionHandler {

	@ExceptionHandler(value = GenericException.class)
	public ResponseEntity<Object> genericException(GenericException ge) {
		return new ResponseEntity<>(ge.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
