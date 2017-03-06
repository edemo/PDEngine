package org.rulez.demokracia.PDEngine.testhelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.PDEngine.IVoteManager;
import org.rulez.demokracia.PDEngine.VoteRegistry;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;

public class CreatedDefaultVoteRegistry {

	public IVoteManager voteManager;
	public VoteAdminInfo adminInfo;
	public String voteName = "VoteInitialValuesTest";

	@Before
	public void setUp() {
		voteManager = new VoteRegistry();
		List<String> neededAssurances = new ArrayList<String>();
		List<String> countedAssurances = new ArrayList<String>();
		boolean isPrivate = true;
		int minEndorsements = 0;
		adminInfo = voteManager.createVote(voteName ,neededAssurances, countedAssurances, isPrivate, minEndorsements);
	}

}