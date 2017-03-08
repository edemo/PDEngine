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

    public static void checkVoteName(String voteName) throws ReportedException {
		checkString(voteName, "vote name");
    }

	static Set<String> checkAssurances(Set<String> neededAssurances, String type) throws ReportedException{

		Set<String> uniques = new HashSet<>();

		for (String assurance : neededAssurances) {
			if(type == "counted" && "".equals(assurance)) {
				assurance = null;				
			} else {
				checkString(assurance, type+" assurance name");				
			}
			uniques.add(assurance);

        }
		return uniques;
	}

	private static void checkString (String inputString, String description) throws ReportedException{

		if(null == inputString) {
			throw new IsNullException(description);
		}
		Pattern p = Pattern.compile("(\\d|\\w)+", Pattern.UNICODE_CHARACTER_CLASS);

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
