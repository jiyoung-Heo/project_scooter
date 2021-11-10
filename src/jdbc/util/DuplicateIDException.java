package jdbc.util;

public class DuplicateIDException extends RuntimeException {
	private static final long serialVersionUID = -6679757398260083966L;

	public DuplicateIDException(String msg) {
		super(msg);
	}
}