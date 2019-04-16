package org.rulez.demokracia.pdengine.exception;

public class TooLongException extends ReportedException {

  private static final long serialVersionUID = 1L;

  public TooLongException(final String description) {
    super("string too long: {0}", description);
  }

}
