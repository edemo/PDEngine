package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("The vote initially can only be modified with the adminkey")
public class VoteInitialValuesTest extends CreatedDefaultVoteRegistry {


	@Test
	public void canAddin_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().voteParameters.canAddin);
	}

	@Test
	public void canEndorse_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().voteParameters.canEndorse);
	}

	@Test
	public void canVote_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().voteParameters.canVote);
	}

	@Test
	public void canView_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().voteParameters.canView);
	}

	@Test
	public void canUpdate_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().canUpdate);
	}
}
