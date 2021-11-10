package jdbc.util;

public class WrongNumberException extends RuntimeException {
	private static final long serialVersionUID = -6679757398260083966L;

	public WrongNumberException(String msg) {
		super(msg);
	}

	public WrongNumberException() {
	}
}