package org.rulez.demokracia.pdengine.vote.validation;

import org.rulez.demokracia.pdengine.exception.IsNullException;
import org.rulez.demokracia.pdengine.exception.TooLongException;
import org.rulez.demokracia.pdengine.exception.TooShortException;

final class StringLengthValidationUtil {

  public static void checkNotTooLong(final String inputString, final String description,
      final int maxLength) {
    if (inputString.length() > maxLength)
      throw new TooLongException(description);
  }

  public static void checkNotTooShort(final String inputString, final String description,
      final int minLength) {
    if (inputString.length() < minLength)
      throw new TooShortException(description);
  }

  public static void checkNotNull(final String inputString, final String description) {
    if (null == inputString)
      throw new IsNullException(description);
  }
}
