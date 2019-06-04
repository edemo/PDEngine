package org.rulez.demokracia.pdengine.vote.validation;

import static org.rulez.demokracia.pdengine.vote.validation.RegexValidationUtil.checkPattern;
import static org.rulez.demokracia.pdengine.vote.validation.StringLengthValidationUtil.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.rulez.demokracia.pdengine.vote.AssuranceType;

public class ValidationUtil {

  public static final int MIN_STRING_LENGTH = 3;
  public static final int MAX_STRING_LENGTH = 255;
  public static final String EMPTY_STRING = "";
  private static final String NO_SPACE_PATTERN = "(\\d|\\w)+";
  private static final String WITH_SPACE_PATTERN = "(\\d| |\\w)+";

  public static void checkVoteName(final String voteName) {
    checkString(voteName, "vote name", WITH_SPACE_PATTERN);
  }

  public static List<String> checkAssurances(final Collection<String> neededAssurances,
      final AssuranceType type) {
    return neededAssurances.stream().map(n -> normalizeAssurance(type, n))
        .collect(Collectors.toList());
  }

  private static String normalizeAssurance(final AssuranceType type, final String assurance) {
    if (AssuranceType.COUNTED.equals(type) && EMPTY_STRING.equals(assurance))
      return null;
    else
      checkString(assurance, type.description + " assurance name", NO_SPACE_PATTERN);
    return assurance;
  }

  private static void checkString(final String inputString, final String description,
      final String pattern) {
    checkStringLength(inputString, description, MIN_STRING_LENGTH, MAX_STRING_LENGTH);
    checkPattern(inputString, description, pattern);
  }

  private static void checkStringLength(final String inputString, final String description,
      final int minStringLength, final int maxStringLength) {
    checkNotNull(inputString, description);
    checkNotTooShort(inputString, description, minStringLength);
    checkNotTooLong(inputString, description, maxStringLength);
  }

}
