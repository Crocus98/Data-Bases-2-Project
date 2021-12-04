package exceptions;

public class InvalidRegistrationParams extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidRegistrationParams(String message) {
		super(message);
	}
}
