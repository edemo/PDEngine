package org.rulez.demokracia.PDEngine.exception;

public class IsNullException extends ReportedException {

	private static final long serialVersionUID = 1L;

	public IsNullException(String description) {
		super("{0} is null", description);
	}

}
