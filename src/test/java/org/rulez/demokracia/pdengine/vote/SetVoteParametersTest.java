package org.rulez.demokracia.pdengine.vote;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;
import org.rulez.demokracia.pdengine.vote.Vote;

@TestedFeature("Manage votes")
@TestedOperation("set vote parameters")
@TestedBehaviour("sets the parameters of the vote")
public class SetVoteParametersTest extends CreatedDefaultVoteRegistry {

	private Vote vote;
	@Override
	@Before
	public void setUp() {
		super.setUp();
		vote = new VariantVote();
		int minEndorsements = 3;
		boolean canAddin = true;
		boolean canEndorse = true;
		boolean canVote = true;
		boolean canView = true;
		vote.setParameters(minEndorsements, canAddin, canEndorse, canVote, canView);
	}

	@Test
	public void set_vote_parameters_sets_the_minEndorsement_parameter_of_the_vote() {
		assertEquals(3, vote.getParameters().getMinEndorsements());
	}

	@Test
	public void set_vote_parameters_sets_the_canAddIn_parameter_of_the_vote() {
		assertTrue(vote.getParameters().isAddinable());
	}

	@Test
	public void set_vote_parameters_sets_the_canEndorse_parameter_of_the_vote() {
		assertTrue(vote.getParameters().isEndorsable());
	}

	@Test
	public void set_vote_parameters_sets_the_canVote_parameter_of_the_vote() {
		assertTrue(vote.getParameters().isVotable());
	}

	@Test
	public void set_vote_parameters_sets_the_canView_parameter_of_the_vote() {
		assertTrue(vote.getParameters().isViewable());
	}
}
