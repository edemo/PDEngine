package org.rulez.demokracia.PDEngine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.annotations.tested_behaviour;
import org.rulez.demokracia.PDEngine.annotations.tested_feature;
import org.rulez.demokracia.PDEngine.annotations.tested_operation;
import org.rulez.demokracia.PDEngine.exception.ReportedException;
import org.rulez.demokracia.PDEngine.testhelpers.CreatedDefaultVoteRegistry;

public class SetVoteParametersTest extends CreatedDefaultVoteRegistry {

	private Vote vote;
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		vote = voteManager.getVote(adminInfo.getVoteId());
		int minEndorsements = 3;
		boolean canAddin = true;
		boolean canEndorse = true;
		boolean canVote = true;
		boolean canView = true;
		vote.setParameters(adminInfo.getAdminKey(),
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
		assertEquals(vote.getMinEndorsements(), 3);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	public void set_vote_parameters_sets_the_canAddIn_parameter_of_the_vote() {
		assertEquals(vote.getCanAddin(), true);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	public void set_vote_parameters_sets_the_canEndorse_parameter_of_the_vote() {
		assertEquals(vote.getCanEndorse(), true);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	public void set_vote_parameters_sets_the_canVote_parameter_of_the_vote() {
		assertEquals(vote.getCanVote(), true);
	}

	@Test
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	public void set_vote_parameters_sets_the_canView_parameter_of_the_vote() {
		assertEquals(vote.getCanView(), true);
	}

}
