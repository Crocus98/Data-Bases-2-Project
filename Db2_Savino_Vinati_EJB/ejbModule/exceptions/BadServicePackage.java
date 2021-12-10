package exceptions;

public class BadServicePackage extends Exception {
	private static final long serialVersionUID = 1L;

	public BadServicePackage(String message) {
		super(message);
	}
}
