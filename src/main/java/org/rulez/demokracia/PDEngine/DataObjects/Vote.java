package org.rulez.demokracia.PDEngine.DataObjects;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.rulez.demokracia.PDEngine.RandomUtils;

public class Vote {

	public String name;
	public List<String> neededAssurances;
	public List<String> countedAssurances;
	public boolean isPrivate;
	public String adminKey;
	public String voteId;
	public long creationTime;
	public int minEndorsements;
	public boolean canAddin;
	public boolean canEndorse;
	public boolean canVote;
	public boolean canView;

	public Vote(String voteName, Collection<String> neededAssurances, Collection<String> countedAssurances, boolean isClosed, int minEndorsements) throws Exception {

		checkVoteName(voteName);

		name = voteName;
		this.adminKey = RandomUtils.createRandomKey();
		this.voteId = RandomUtils.createRandomKey();
		this.neededAssurances = new ArrayList<String>(neededAssurances);
		this.countedAssurances = new ArrayList<String>(countedAssurances);
		this.isPrivate = isClosed;
		this.minEndorsements = minEndorsements;
		this.creationTime = Instant.now().getEpochSecond();

	}

	public static void checkVoteName(String voteName) throws Exception{


		if (voteName.length() < 3) {
			Exception e = new Exception("Vote name is too short!");

			throw e;
		}

		if (voteName.length() > 255) {
			Exception e = new Exception("Vote name is too long!");

			throw e;
		}

		if (!voteName.matches("(\\d|\\w)+")) {
			//[^\W\d_] or [a-zA-Z].

			Exception e = new Exception("Wrong characters in the vote name!");

			throw e;
		}

	}

}
