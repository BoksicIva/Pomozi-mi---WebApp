package NULL.DTPomoziMi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityMissingException extends RuntimeException {

	private static final long serialVersionUID = -3022782532632094330L;

	public EntityMissingException(Class<?> cls, Object ref) {
		super("Entity with reference " + ref + " of " + cls + " not found.");
	}
	
}
