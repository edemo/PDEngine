package org.rulez.demokracia.PDEngine.exception;

public class TooShortException extends ReportedException {

	private static final long serialVersionUID = 1L;

	public TooShortException(String description) {
		super("string too short: {0}", description);
	}

}
