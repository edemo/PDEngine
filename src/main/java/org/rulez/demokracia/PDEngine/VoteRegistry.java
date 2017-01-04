package org.rulez.demokracia.PDEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;

public class VoteRegistry {
	private static Map<String, Vote> votes = new HashMap<String, Vote>();

	public static VoteAdminInfo create(
			String voteName,
			List<String> neededAssurances,
			List<String> countedAssurances,
			boolean isPrivate,
			int minEndorsements) {
		VoteAdminInfo admininfo = new VoteAdminInfo();
		admininfo.adminKey = RandomUtils.createRandomKey();
		Vote vote = new Vote(voteName);
		votes.put(admininfo.adminKey, vote);
		return admininfo;
	}

	public static Vote getByKey(String adminKey) {
		return votes.get(adminKey);
	}

}
