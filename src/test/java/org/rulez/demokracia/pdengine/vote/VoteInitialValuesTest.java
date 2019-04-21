package org.rulez.demokracia.pdengine.vote;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;
import org.rulez.demokracia.pdengine.vote.Vote;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("The vote initially can only be modified with the adminkey")
public class VoteInitialValuesTest {


	@Test
	public void canAddin_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().getParameters().isAddinable());
	}

	@Test
	public void canEndorse_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().getParameters().isEndorsable());
	}

	@Test
	public void canVote_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().getParameters().isVotable());
	}

	@Test
	public void canView_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().getParameters().isViewable());
	}

	@Test
	public void canUpdate_is_false_in_the_created_vote() {
		assertEquals(false, getTheVote().getParameters().isUpdatable());
	}

	private Vote getTheVote() {
		return new VariantVote();
	}
}
