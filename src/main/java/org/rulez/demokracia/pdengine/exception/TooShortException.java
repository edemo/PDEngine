package org.rulez.demokracia.pdengine.exception;

public class TooShortException extends ReportedException {

	private static final long serialVersionUID = 1L;

	public TooShortException(final String description) {
		super("string too short: {0}", description);
	}

}
