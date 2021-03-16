package eu.gael67350.api.errors.schemas;

import java.sql.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class DefaultErrorSchema {

	private Date timestamp;
	private HttpStatus status;
	private String message;
	private Map<String, String> errors;
	
	public Date getTimestamp() {
		return timestamp;
	}
	public int getStatus() {
		return status.value();
	}
	public String getMessage() {
		return message;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	
}
