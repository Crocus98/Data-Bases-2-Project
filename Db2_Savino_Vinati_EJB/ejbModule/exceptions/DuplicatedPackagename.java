package exceptions;

public class DuplicatedPackagename extends Exception {
	private static final long serialVersionUID = 1L;

	public DuplicatedPackagename(String message) {
		super(message);
	}
}
