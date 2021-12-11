package exceptions;

public class BadOrderParams extends Exception {
	private static final long serialVersionUID = 1L;

	public BadOrderParams(String message) {
		super(message);
	}
}
