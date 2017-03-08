package org.rulez.demokracia.pdengine.testhelpers;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.rulez.demokracia.pdengine.IVoteManager;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class CreatedDefaultVoteRegistry extends ThrowableTester{

	public IVoteManager voteManager;
	public VoteAdminInfo adminInfo;
	public String voteName = "VoteInitialValuesTest";

	protected Set<String> neededAssurances;
	protected Set<String> countedAssurances;
	protected boolean isPrivate;
	protected int minEndorsements;

	@Before
	public void setUp() throws ReportedException {
		voteManager = IVoteManager.getVoteManager();
		neededAssurances = new HashSet<>();
		countedAssurances = new HashSet<>();
		isPrivate = true;
		minEndorsements = 0;
		neededAssurances.add("magyar");
        voteName = "testVote";
		adminInfo = createAVote();
	}

	protected VoteAdminInfo createAVote() throws ReportedException {
		return voteManager.createVote(voteName, neededAssurances, countedAssurances, isPrivate, minEndorsements );
	}

}