package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class ForShowVoteTheUserNeedsACountedAssurance extends CreatedDefaultVoteRegistry {
	
	public ForShowVoteTheUserNeedsACountedAssurance() {
		super();
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("if adminKey is anon, the user should have any of the countedAssurances")
	public void a_user_with_not_all_assourances_cannot_show_the_vote() throws ReportedException {
	
		Vote vote = getTheVote();
		vote.countedAssurances.add("german");

		assertAssurancesMissing(vote);
	}
	
	@Test
	@tested_feature("Manage votes")
	@tested_operation("show vote")
	@tested_behaviour("if adminKey is anon, the user should have any of the countedAssurances")
	public void a_user_with_not_all_assourances_cannot_show_the_vote_even_with_more_assurances() throws ReportedException {
	
		Vote vote = getTheVote();
		vote.countedAssurances.add("magyar");
		vote.countedAssurances.add("german");

		assertAssurancesMissing(vote);
	}

	private void assertAssurancesMissing(Vote vote) {
		assertThrows(
				() -> voteManager.showVote(vote.id, "user")
			).assertMessageIs("missing assurances");
	}
}
