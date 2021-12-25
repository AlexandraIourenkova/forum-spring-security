package telran.b7a.forumMyVersion.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundExeption extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1175483733902639774L;
	
	public PostNotFoundExeption(String PostId) {
		super("Post with id " + PostId + " not found");
	}
}
