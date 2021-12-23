package exceptions;

public class BadAlert extends Exception {
	private static final long serialVersionUID = 1L;

	public BadAlert(String message) {
		super(message);
	}
}
