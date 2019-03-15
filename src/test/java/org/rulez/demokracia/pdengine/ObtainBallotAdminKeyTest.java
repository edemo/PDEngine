package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class ObtainBallotAdminKeyTest extends CreatedDefaultChoice{


	@Before
	public void setUp() throws ReportedException {
		super.setUp();
	}

	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void if_the_user_does_not_have_all_the_needed_assurances_then_she_cannot_vote() {
		Vote vote = voteManager.getVote(adminInfo.voteId);
		vote.neededAssurances.clear();
		vote.neededAssurances.add("dontCare");
		assertThrows(
				() -> voteManager.obtainBallot(adminInfo.voteId, "anon")
			).assertMessageIs("The user does not have all of the needed assurances.");
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void if_the_user_does_have_all_the_assurances_then_a_ballot_is_served() {
		Vote vote = voteManager.getVote(adminInfo.voteId);
		vote.neededAssurances.clear();
		vote.neededAssurances.add("magyar");
		
		String ballot = voteManager.obtainBallot(adminInfo.voteId, "anon");
		assertTrue(ballot instanceof String);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("if adminKey is anon, the user should have all the neededAssurances")
	@Test
	public void if_neededAssurances_is_empty_then_a_ballot_is_served_to_anyone() {
		Vote vote = voteManager.getVote(adminInfo.voteId);
		vote.neededAssurances.clear();
		
		String ballot = voteManager.obtainBallot(adminInfo.voteId, "anon");
		assertTrue(ballot instanceof String);
	}
}
