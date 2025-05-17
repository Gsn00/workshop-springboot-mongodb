package app.resources.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long timestamp;
	private HttpStatus status;
	private String error;
	private String path;
}
