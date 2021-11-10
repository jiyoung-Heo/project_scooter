package jdbc.util;

public class AlreadyRentalException extends RuntimeException {
	private static final long serialVersionUID = -6679757398260083966L;

	public AlreadyRentalException(String msg) {
		super(msg);
	}

	public AlreadyRentalException() {
	}
}
