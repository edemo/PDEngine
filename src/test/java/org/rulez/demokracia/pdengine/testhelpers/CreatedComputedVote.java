package org.rulez.demokracia.pdengine.testhelpers;

import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.ComputedVote;
import org.rulez.demokracia.pdengine.VoteResult;

public class CreatedComputedVote extends CreatedDefaultCastVoteWithRankedChoices {

	protected ComputedVote computedVote;
	protected List<VoteResult> result;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		getTheVote().votesCast = castVote;
		computedVote = new ComputedVote(getTheVote());
		result = computedVote.computeVote();
	}

}
