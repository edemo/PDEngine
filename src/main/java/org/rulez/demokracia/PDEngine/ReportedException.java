package org.rulez.demokracia.PDEngine;

public class ReportedException extends Exception {

	private static final long serialVersionUID = 3322550743512295289L;

	public ReportedException () {

    }

    public ReportedException (String message) {
        super (message);
    }

    public ReportedException (Throwable cause) {
        super (cause);
    }

    public ReportedException (String message, Throwable cause) {
        super (message, cause);
    }

}
