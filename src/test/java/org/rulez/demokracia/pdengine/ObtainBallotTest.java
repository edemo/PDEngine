package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class ObtainBallotTest extends CreatedDefaultChoice{

	private Vote vote;

	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		vote = this.getTheVote();
	}

	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("creates a new ballot with an id for the vote")
	@Test
	public void obtain_ballot_returns_the_ballot_string() {
		String ballot = voteManager.obtainBallot(vote.id,vote.adminKey);
		assertTrue(ballot instanceof String);
	}

	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("creates a new ballot with an id for the vote")
	@Test
	public void two_ballots_are_different() {
		String ballot1 = voteManager.obtainBallot(vote.id,vote.adminKey);
		String ballot2 = voteManager.obtainBallot(vote.id,vote.adminKey);
		assertNotEquals(ballot1,ballot2);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("validates inputs")
	@Test
	public void voteId_is_checked_to_be_the_id_of_an_existig_vote() {
		assertThrows(() -> {
			voteManager.obtainBallot("invalidvoteId",vote.adminKey);
		})
				.assertException(IllegalArgumentException.class)
				.assertMessageIs("illegal voteId: invalidvoteId");

	}

	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("validates inputs")
	@Test
	public void adminKey_is_checked() {
		assertThrows(() -> {
			voteManager.obtainBallot(vote.id,"illegalAdminKey");
		})
				.assertException(IllegalArgumentException.class)
				.assertMessageIs("Illegal adminKey: illegalAdminKey");

	}

	@tested_feature("Manage votes")
	@tested_operation("Obtain ballot")
	@tested_behaviour("creates a new ballot with an id for the vote")
	@Test
	public void obtainBallot_stores_the_ballot() {
		String ballot = voteManager.obtainBallot(adminInfo.voteId, adminInfo.adminKey);
		Vote vote = getTheVote();
		assertTrue(vote.ballots.contains(ballot));
	}



}
