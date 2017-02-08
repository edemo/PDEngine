package org.rulez.demokracia.PDEngine;

import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoteRegistry  {

	private static Map<String, Vote> votes = new HashMap<>();
	private enum  ErrorReason {STR_LONG,STR_SHORT,CHAR_NOT_VALID,DUPLICATE_VALUE,READY}


    public static VoteAdminInfo create(
			String voteName,
			List<String> neededAssurances,
			List<String> countedAssurances,
			boolean isClosed,
			int minEndorsements) throws ReportedException {

        checkVoteName(voteName);
        checkNeededAssurances(neededAssurances);
        checkCountedAssurances(countedAssurances);

		VoteAdminInfo admininfo = new VoteAdminInfo();
		Vote vote = new Vote (voteName, neededAssurances, countedAssurances, isClosed, minEndorsements);
		admininfo.adminKey=vote.adminKey;
		admininfo.voteId= vote.voteId;
		votes.put(admininfo.adminKey, vote);
		return admininfo;
	}

	public static Vote getByKey(String adminKey) {
		return votes.get(adminKey);
	}

    public static void checkVoteName(String voteName) throws ReportedException {


		ErrorReason checkValue = checkString(voteName);

		if (checkValue == ErrorReason.STR_SHORT) {
            throw new ReportedException("Vote name is too short!");
		} else if (checkValue == ErrorReason.STR_LONG) {
            throw new ReportedException("Vote name is too long!");
		} else if (checkValue ==ErrorReason.CHAR_NOT_VALID ) {
            throw new ReportedException("Wrong characters in the vote name!");
		}


    }

    public static void checkNeededAssurances (List<String> assurance) throws ReportedException {

		ErrorReason checkValue = checkAssurances(assurance);

		if (checkValue == ErrorReason.STR_SHORT) {
            throw new ReportedException("neededAssurance is too short!");
		} else if (checkValue == ErrorReason.STR_LONG) {
            throw new ReportedException("neededAssurance is too long!");
		} else if (checkValue == ErrorReason.CHAR_NOT_VALID) {
            throw new ReportedException("Wrong characters in the neededAssurance!");
        } else if (checkValue == ErrorReason.DUPLICATE_VALUE) {
            throw new ReportedException("neededAssurance contains duplicated values!");
        }
	}

    public static void checkCountedAssurances (List<String> assurance) throws ReportedException {

        ErrorReason checkValue = checkAssurances(assurance);

        if (checkValue == ErrorReason.STR_SHORT) {
            throw new ReportedException("countedAssurance is too short!");
        } else if (checkValue == ErrorReason.STR_LONG) {
            throw new ReportedException("countedAssurance is too long!");
        } else if (checkValue == ErrorReason.CHAR_NOT_VALID) {
            throw new ReportedException("Wrong characters in the countedAssurance!");
        } else if (checkValue == ErrorReason.DUPLICATE_VALUE) {
            throw new ReportedException("countedAssurance contains duplicated values!");
        }
    }

	private static ErrorReason checkAssurances(List<String> list) throws ReportedException{

		//Set<T> duplicates = new LinkedHashSet<T>();

		Set<String> uniques = new HashSet<>();
		ErrorReason ret = ErrorReason.READY;

		for (String temp : list) {

			ret = checkString(temp);

			if (!uniques.add(temp)) {
				ret = ErrorReason.DUPLICATE_VALUE;
			}

			if (ret != ErrorReason.READY ){
                return ret;
			}
        }

        return ret;
	}

	private static ErrorReason checkString (String inputString){



		Pattern p = Pattern.compile("(\\d|\\w)+", Pattern.UNICODE_CHARACTER_CLASS);

		Matcher m = p.matcher(inputString);

		if (inputString.length() < 3) {
			return ErrorReason.STR_SHORT;
		}

		if (inputString.length() > 255) {
			return ErrorReason.STR_LONG;
		}

		if (!m.matches()) {
			//(\d|\w)+
			return ErrorReason.CHAR_NOT_VALID;
		}

		return ErrorReason.READY;
	}

}
