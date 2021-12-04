package exceptions;

public class DuplicatedUsername extends Exception {
	private static final long serialVersionUID = 1L;

	public DuplicatedUsername(String message) {
		super(message);
	}
}
