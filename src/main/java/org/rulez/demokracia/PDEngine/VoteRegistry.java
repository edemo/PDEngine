package org.rulez.demokracia.PDEngine;

import java.io.IOException;
import java.util.*;

import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;

public class VoteRegistry  {
	private static Map<String, Vote> votes = new HashMap<String, Vote>();

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
			ReportedException e = new ReportedException("Vote name is too short!");
			throw e;
		} else if (checkValue == 2) {
			ReportedException e = new ReportedException("Vote name is too long!");
			throw e;
		} else if (checkValue == 3) {
			ReportedException e = new ReportedException("Wrong characters in the vote name!");
			throw e;
		}

    }

    public static void checkNeededAssurances (List<String> assurance) throws ReportedException {

		Integer checkValue = checkAssurances(assurance);

		if (checkValue == 1) {
			ReportedException e = new ReportedException("neededAssurance is too short!");
			throw e;
		} else if (checkValue == 2) {
			ReportedException e = new ReportedException("neededAssurance is too long!");
			throw e;
		} else if (checkValue == 3) {
			ReportedException e = new ReportedException("Wrong characters in the vote name!");
			throw e;
        } else if (checkValue == 4) {
            ReportedException e = new ReportedException("neededAssurance contains duplicated values!");
            throw e;
        }
	}

	private static Integer checkAssurances(List<String> list) throws ReportedException{

		//Set<T> duplicates = new LinkedHashSet<T>();
		Set<String> uniques = new HashSet<String>();
		Integer ret =0;

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

		if (inputString.length() < 3) {
			return 1;
		}

		if (inputString.length() > 255) {
			return 2;
		}

		if (!inputString.matches("(\\d|\\w)+")) {
			//(\d|\w)+
			//[^\W\d_] or [a-zA-Z].
			//        /\`|\~|\!|\@|\#|\$|\%|\^|\&|\*|\(|\)|\+|\=|\[|\{|\]|\}|\||\\|\'|\<|\,|\.|\>|\?|\/|\""|\;|\:|\s/g
			return 3;
		}

		return 0;
	}

}
