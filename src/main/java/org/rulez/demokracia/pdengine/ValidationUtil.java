package org.rulez.demokracia.pdengine;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rulez.demokracia.pdengine.exception.IsNullException;
import org.rulez.demokracia.pdengine.exception.NotValidCharsException;
import org.rulez.demokracia.pdengine.exception.TooLongException;
import org.rulez.demokracia.pdengine.exception.TooShortException;

public class ValidationUtil {

	public static final int MIN_STRING_LENGTH = 3;
	public static final int MAX_STRING_LENGTH = 255;
	public static final String EMPTY_STRING = "";
	public static final String COUNTED = "counted";

    public static void checkVoteName(final String voteName) {
		checkStringWithSpaces(voteName, "vote name");
    }

	public static Set<String> checkAssurances(final Set<String> neededAssurances, final String type) {

		Set<String> uniques = new HashSet<>();

		for (String assurance : neededAssurances) {
			final Set<String> uniques1 = uniques;
			final String assurance1 = assurance;
			String normalizedAssurance = normalizeAssurance(type, assurance1);
			uniques1.add(normalizedAssurance);
        }
		
		return uniques;
	}

	private static String normalizeAssurance(final String type, final String assurance) {
		if(COUNTED.equals(type) && EMPTY_STRING.equals(assurance)) {
			return null;
		} else {
			checkStringNoSpaces(assurance, type+" assurance name");				
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
