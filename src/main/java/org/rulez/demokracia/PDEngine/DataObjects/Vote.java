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

	public Vote(String voteName, Collection<String> neededAssurances, Collection<String> countedAssurances, boolean isClosed, int minEndorsements) {
		name = voteName;
		this.adminKey = RandomUtils.createRandomKey();
		this.voteId = RandomUtils.createRandomKey();
		this.neededAssurances = new ArrayList<String>(neededAssurances);
		this.countedAssurances = new ArrayList<String>(countedAssurances);
		this.isPrivate = isClosed;
		this.minEndorsements = minEndorsements;
		this.creationTime = Instant.now().getEpochSecond();

	}

}
