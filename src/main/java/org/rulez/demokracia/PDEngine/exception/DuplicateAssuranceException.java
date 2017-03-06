package org.rulez.demokracia.PDEngine.exception;

public class DuplicateAssuranceException extends ReportedException {
	private static final long serialVersionUID = 1L;

	public DuplicateAssuranceException(String type) {
		super(String.format("duplicate %s assurances", type));
	}

}
