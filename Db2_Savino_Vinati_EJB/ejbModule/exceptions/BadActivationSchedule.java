package exceptions;

public class BadActivationSchedule extends Exception {
	private static final long serialVersionUID = 1L;

	public BadActivationSchedule(String message) {
		super(message);
	}
}
