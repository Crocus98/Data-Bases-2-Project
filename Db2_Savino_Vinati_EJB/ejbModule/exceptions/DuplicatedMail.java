package exceptions;

public class DuplicatedMail extends Exception {
	private static final long serialVersionUID = 1L;

	public DuplicatedMail(String message) {
		super(message);
	}
}
