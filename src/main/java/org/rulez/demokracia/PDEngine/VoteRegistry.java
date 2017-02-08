package org.rulez.demokracia.PDEngine;

import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VoteRegistry  {
	private static Map<String, Vote> votes = new HashMap<>();

	public static VoteAdminInfo create(
			String voteName,
			List<String> neededAssurances,
			List<String> countedAssurances,
			boolean isClosed,
			int minEndorsements) throws ReportedException {

        checkVoteName(voteName);

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


		Integer checkValue = checkString(voteName);

		if (checkValue == 1) {
            throw new ReportedException("Vote name is too short!");
		} else if (checkValue == 2) {
            throw new ReportedException("Vote name is too long!");
		} else if (checkValue == 3) {
            throw new ReportedException("Wrong characters in the vote name!");
		}


    }

    public static void checkNeededAssurances (List<String> assurance) throws ReportedException {

		Integer checkValue = checkAssurances(assurance);

		if (checkValue == 1) {
            throw new ReportedException("neededAssurance is too short!");
		} else if (checkValue == 2) {
            throw new ReportedException("neededAssurance is too long!");
		} else if (checkValue == 3) {
            throw new ReportedException("Wrong characters in the vote name!");
        } else if (checkValue == 4) {
            throw new ReportedException("neededAssurance contains duplicated values!");
        }
	}

	private static Integer checkAssurances(List<String> list) throws ReportedException{

		//Set<T> duplicates = new LinkedHashSet<T>();

		Set<String> uniques = new HashSet<>();
		Integer ret;

		for (String temp : list) {

			ret = checkString(temp);

			if (!uniques.add(temp)) {
				ret = 4;
			}

			if (ret != 0){
				return ret;
			}
		}

		return 0;

	}

	private static Integer checkString (String inputString){
		// return 0: String ready
		// return 1 too short
		//2 too long
		//3 contains wroeng cahracter

		Pattern p = Pattern.compile("(\\d|\\w)+", Pattern.UNICODE_CHARACTER_CLASS);

		Matcher m = p.matcher(inputString);

		if (inputString.length() < 3) {
			return 1;
		}

		if (inputString.length() > 255) {
			return 2;
		}

		if (!m.matches()) {
			//(\d|\w)+
			return 3;
		}

		return 0;
	}

}
