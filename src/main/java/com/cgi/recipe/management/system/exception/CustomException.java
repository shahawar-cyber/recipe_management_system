package com.cgi.recipe.management.system.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.cgi.recipe.management.system.exception.CustomRuntimeException;
import com.cgi.recipe.management.system.exception.ErrorMessage;

@ControllerAdvice
public class CustomException {

	@ExceptionHandler(value = CustomRuntimeException.class)
	public ResponseEntity<ErrorMessage> handleCustomRuntimeException(CustomRuntimeException exception,
			WebRequest request) {
		ErrorMessage errorMessage = buildErrorMessage(exception.getMessage(), request, exception.getDetail());
		return new ResponseEntity<>(errorMessage, exception.getHttpStatus());
	}

	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException exception, WebRequest request) {
		ErrorMessage errorMessage = buildErrorMessage(exception.getMessage(), request,
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorMessage buildErrorMessage(String exception, WebRequest request, String detail) {
		String path = request.getDescription(false).split("=")[1];
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setLocalDateTime(LocalDateTime.now());
		errorMessage.setMessage(exception);
		errorMessage.setPath(path);
		errorMessage.setDetail(detail);
		return errorMessage;
	}

}
