package org.rulez.demokracia.pdengine.vote.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.rulez.demokracia.pdengine.exception.NotValidCharsException;

final class RegexValidationUtil {

  public static void checkPattern(final String inputString, final String description,
      final String patternString) {
    Pattern pattern = Pattern.compile(patternString, Pattern.UNICODE_CHARACTER_CLASS);
    Matcher matcher = pattern.matcher(inputString);

    if (!matcher.matches())
      throw new NotValidCharsException(description);
  }
}
