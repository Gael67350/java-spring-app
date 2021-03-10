package eu.gael67350.api.errors;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers,
            HttpStatus status, WebRequest request) {
		Map <String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		
		Map<String, String> mapErrors = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(x -> mapErrors.put(x.getField(), x.getDefaultMessage()));
		
		body.put("errors", mapErrors);
		return new ResponseEntity<>(body, headers, status);
	}
	
}
