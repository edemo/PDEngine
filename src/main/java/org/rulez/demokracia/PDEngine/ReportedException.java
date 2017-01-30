package org.rulez.demokracia.PDEngine;

/**
 * Created by krisz on 2017. 01. 30..
 */
public class ReportedException extends Exception {
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
