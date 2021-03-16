package eu.gael67350.api.errors.handlers;

import java.util.Date;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import eu.gael67350.api.errors.exceptions.UserNotFoundException;
import eu.gael67350.api.errors.schemas.DefaultErrorSchema;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ApiResponse(responseCode = "400", description = "Requête incorrecte", content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = DefaultErrorSchema.class)))
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
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ApiResponse(responseCode = "404", description = "Ressource non trouvée", content = @Content(mediaType = "application/json",
    schema = @Schema(implementation = DefaultErrorSchema.class)))
	public ResponseEntity<Object> handleUserNotFound(RuntimeException ex) {
		Map <String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", new Date());
		body.put("status", HttpStatus.NOT_FOUND);
		body.put("message", ex.getMessage());
		
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
}
