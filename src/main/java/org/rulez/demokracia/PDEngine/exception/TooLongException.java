package org.rulez.demokracia.PDEngine.exception;

public class TooLongException extends ReportedException {
	
	private static final long serialVersionUID = 1L;

	public TooLongException(String description) {
		super("string too long: {0}", description);
	}

}
