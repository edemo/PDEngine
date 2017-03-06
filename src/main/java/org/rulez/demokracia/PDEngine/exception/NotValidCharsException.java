package org.rulez.demokracia.PDEngine.exception;

public class NotValidCharsException extends ReportedException {

	private static final long serialVersionUID = 1L;

	public NotValidCharsException(String description) {
		super("invalid characters in {0}", description);
	}
}
