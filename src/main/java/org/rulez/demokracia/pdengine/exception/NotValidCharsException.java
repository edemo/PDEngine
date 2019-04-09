package org.rulez.demokracia.pdengine.exception;

public class NotValidCharsException extends ReportedException {

  private static final long serialVersionUID = 1L;

  public NotValidCharsException(final String description) {
    super("invalid characters in {0}", description);
  }
}
