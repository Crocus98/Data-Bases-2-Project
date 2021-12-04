package exceptions;

public class InvalidUsertype extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidUsertype(String message) {
		super(message);
	}
}
