package app.dto;

import java.io.Serializable;

import app.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	
	public AuthorDTO(User user) {
		this.id = user.getId();
		this.name = user.getName();
	}
}
