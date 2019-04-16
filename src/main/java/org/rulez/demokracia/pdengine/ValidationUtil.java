package org.rulez.demokracia.pdengine;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.rulez.demokracia.pdengine.exception.IsNullException;
import org.rulez.demokracia.pdengine.exception.NotValidCharsException;
import org.rulez.demokracia.pdengine.exception.TooLongException;
import org.rulez.demokracia.pdengine.exception.TooShortException;
import org.rulez.demokracia.pdengine.vote.AssuranceType;

public class ValidationUtil {

	public static final int MIN_STRING_LENGTH = 3;
	public static final int MAX_STRING_LENGTH = 255;
	public static final String EMPTY_STRING = "";

	public static void checkVoteName(final String voteName) {
		checkStringWithSpaces(voteName, "vote name");
	}

	public static List<String> checkAssurances(final Collection<String> neededAssurances, final AssuranceType type) {
		return neededAssurances.stream()
				.map(n -> normalizeAssurance(type, n))
				.collect(Collectors.toList());
	}

	private static String normalizeAssurance(final AssuranceType type, final String assurance) {
		if (AssuranceType.COUNTED.equals(type) && EMPTY_STRING.equals(assurance)) {
			return null;
		} else {
			checkStringNoSpaces(assurance, type.name + " assurance name");
		}
		return assurance;
	}

	public static void checkStringNoSpaces(final String inputString, final String description) {
		String noSpaceString = "(\\d|\\w)+";
		checkStringForPattern(inputString, description, noSpaceString);
	}

	public static void checkStringWithSpaces(final String inputString, final String description) {
		String noSpaceString = "(\\d| |\\w)+";
		checkStringForPattern(inputString, description, noSpaceString);
	}

	public static void checkStringForPattern(final String inputString, final String description, final String noSpaceString)
			throws IsNullException, TooShortException, TooLongException, NotValidCharsException {
		checkNotNull(inputString, description);
		checkNotTooShort(inputString, description);
		checkNotTooLong(inputString, description);
		checkAllValidChars(inputString, description, noSpaceString);
	}

	public static void checkAllValidChars(final String inputString, final String description, final String noSpaceString) throws NotValidCharsException {
		Pattern pattern = Pattern.compile(noSpaceString, Pattern.UNICODE_CHARACTER_CLASS);
		Matcher matcher = pattern.matcher(inputString);

		if (!matcher.matches()) {
			throw new NotValidCharsException(description);
		}
	}

	public static void checkNotTooLong(final String inputString, final String description) throws TooLongException {
		if (inputString.length() > MAX_STRING_LENGTH) {
			throw new TooLongException(description);
		}
	}

	public static void checkNotTooShort(final String inputString, final String description) throws TooShortException {
		if (inputString.length() < MIN_STRING_LENGTH) {
			throw new TooShortException(description);
		}
	}

	public static void checkNotNull(final String inputString, final String description) throws IsNullException {
		if(null == inputString) {
			throw new IsNullException(description);
		}
	}

}
