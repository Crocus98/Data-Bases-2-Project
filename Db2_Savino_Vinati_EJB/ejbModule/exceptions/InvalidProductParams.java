package exceptions;

public class InvalidProductParams extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidProductParams(String message) {
		super(message);
	}
}
