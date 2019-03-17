package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class SetVoteParametersTest extends CreatedDefaultVoteRegistry {

	private Vote vote;
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		vote = voteManager.getVote(adminInfo.voteId);
		int minEndorsements = 3;
		boolean canAddin = true;
		boolean canEndorse = true;
		boolean canVote = true;
		boolean canView = true;
		vote.setParameters(
				minEndorsements,
				canAddin,
				canEndorse,
				canVote,
				canView);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	public void set_vote_parameters_sets_the_minEndorsement_parameter_of_the_vote() {
		assertEquals(3,vote.minEndorsements);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	public void set_vote_parameters_sets_the_canAddIn_parameter_of_the_vote() {
		assertEquals(true,vote.canAddin);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	public void set_vote_parameters_sets_the_canEndorse_parameter_of_the_vote() {
		assertEquals(true,vote.canEndorse);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	public void set_vote_parameters_sets_the_canVote_parameter_of_the_vote() {
		assertEquals(true,vote.canVote);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	public void set_vote_parameters_sets_the_canView_parameter_of_the_vote() {
		assertEquals(true,vote.canView);
	}

}
