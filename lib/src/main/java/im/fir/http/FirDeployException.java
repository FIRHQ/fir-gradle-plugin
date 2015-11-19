package im.fir.http;

public class FirDeployException extends Exception {
	private static final long serialVersionUID = -2988688153054198188L;

	public FirDeployException() {
	}

	public FirDeployException(String message, Throwable t) {
		super(message, t);
	}

	public FirDeployException(String message) {
		super(message);
	}

	public FirDeployException(Throwable t) {
		super(t);
	}
}
