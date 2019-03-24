package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;

public class VoteModificationInvariantsObservedTest extends InvariantTesting {

	@TestedFeature("Manage votes")
	@TestedOperation("modify vote")
	@TestedBehaviour("vote invariants")
	@Test
	public void vote_invariants_are_observerd_in_modify_vote() {
		Vote vote = getTheVote();
		saveInvariables(vote);
		voteManager.modifyVote(new VoteAdminInfo(savedVoteId, savedAdminKey), "modifiedVoteName");
		vote = voteManager.getVote(adminInfo.voteId);
		assertInvariables(vote);
	}
}
