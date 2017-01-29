package org.rulez.demokracia.PDEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;

public class VoteRegistry  {
	private static Map<String, Vote> votes = new HashMap<String, Vote>();

	public static VoteAdminInfo create(
			String voteName,
			List<String> neededAssurances,
			List<String> countedAssurances,
			boolean isClosed,
			int minEndorsements) throws  Exception {



		VoteAdminInfo admininfo = new VoteAdminInfo();
		Vote vote = new Vote(voteName, neededAssurances, countedAssurances, isClosed, minEndorsements);
		admininfo.adminKey=vote.adminKey;
		admininfo.voteId= vote.voteId;
		votes.put(admininfo.adminKey, vote);
		return admininfo;
	}

	public static Vote getByKey(String adminKey) {
		return votes.get(adminKey);
	}

}
