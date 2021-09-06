package com.example.demo.excecao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

	private static final String CONST_VALIDATION_LENGTH = "Length";
	private static final String CONST_VALIDATION_NOT_BLANK = "NotBlank";
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request){
		String msgUsuario = "Recurso não encontrado";
		String msgDev = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(msgUsuario, msgDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Erro> erros = gerarListaErros(ex.getBindingResult());

		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	private List<Erro> gerarListaErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();

		bindingResult.getFieldErrors().forEach(e -> {
			String msgUsuario = tratarMsgUsr(e);
			String msgDev = e.toString();
			erros.add(new Erro(msgUsuario, msgDev));
		});

		return erros;
	}

	private String tratarMsgUsr(FieldError e) {
		if (e.getCode().equals(CONST_VALIDATION_NOT_BLANK)) {
			return e.getDefaultMessage() + " é obrigatorio.";
		}
		if (e.getCode().equals(CONST_VALIDATION_LENGTH)) {
			return e.getDefaultMessage() + " deve estar entre " + 
				   e.getArguments()[2] + " e " + e.getArguments()[1];
		}
		return e.toString();
	}

}
