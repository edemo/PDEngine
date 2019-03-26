package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("set vote parameters")
@TestedBehaviour("sets the parameters of the vote")
public class SetVoteParametersTest extends CreatedDefaultVoteRegistry {

	private Vote vote;
	@Override
	@Before
	public void setUp() {
		super.setUp();
		vote = voteManager.getVote(adminInfo.voteId);
		int minEndorsements = 3;
		boolean canAddin = true;
		boolean canEndorse = true;
		boolean canVote = true;
		boolean canView = true;
		VoteParameters voteParameters = new VoteParameters(
				minEndorsements,
				canAddin,
				canEndorse,
				canVote,
				canView);
		vote.setParameters(
				voteParameters);
	}

	@Test
	public void set_vote_parameters_sets_the_minEndorsement_parameter_of_the_vote() {
		assertEquals(3, vote.voteParameters.minEndorsements);
	}

	@Test
	public void set_vote_parameters_sets_the_canAddIn_parameter_of_the_vote() {
		assertEquals(true, vote.voteParameters.canAddin);
	}

	@Test
	public void set_vote_parameters_sets_the_canEndorse_parameter_of_the_vote() {
		assertEquals(true, vote.voteParameters.canEndorse);
	}

	@Test
	public void set_vote_parameters_sets_the_canVote_parameter_of_the_vote() {
		assertEquals(true, vote.voteParameters.canVote);
	}

	@Test
	public void set_vote_parameters_sets_the_canView_parameter_of_the_vote() {
		assertEquals(true, vote.voteParameters.canView);
	}
}
