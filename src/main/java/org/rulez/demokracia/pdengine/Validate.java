package org.rulez.demokracia.pdengine;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rulez.demokracia.pdengine.exception.IsNullException;
import org.rulez.demokracia.pdengine.exception.NotValidCharsException;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.exception.TooLongException;
import org.rulez.demokracia.pdengine.exception.TooShortException;

public class Validate {

	private Validate() {
	}

    public static void checkVoteName(String voteName) throws ReportedException {
		checkStringWithSpaces(voteName, "vote name");
    }

	static Set<String> checkAssurances(Set<String> neededAssurances, String type) throws ReportedException{

		Set<String> uniques = new HashSet<>();

		for (String assurance : neededAssurances) {
			if(type.equals("counted") && "".equals(assurance)) {
				assurance = null;				
			} else {
				checkStringNoSpaces(assurance, type+" assurance name");				
			}
			uniques.add(assurance);

        }
		return uniques;
	}

	private static void checkStringNoSpaces (String inputString, String description) throws ReportedException{
		String noSpaceString = "(\\d|\\w)+";
		checkStringForPattern(inputString, description, noSpaceString);
	}

	private static void checkStringWithSpaces(String inputString, String description) throws ReportedException {
		String noSpaceString = "(\\d| |\\w)+";
		checkStringForPattern(inputString, description, noSpaceString);
	}

	private static void checkStringForPattern(String inputString, String description, String noSpaceString)
			throws IsNullException, TooShortException, TooLongException, NotValidCharsException {
		if(null == inputString) {
			throw new IsNullException(description);
		}
		Pattern p = Pattern.compile(noSpaceString, Pattern.UNICODE_CHARACTER_CLASS);

		Matcher m = p.matcher(inputString);

		if (inputString.length() < 3) {
			throw new TooShortException(description);
		}

		if (inputString.length() > 255) {
			throw new TooLongException(description);
		}

		if (!m.matches()) {
			throw new NotValidCharsException(description);
		}
	}

}
