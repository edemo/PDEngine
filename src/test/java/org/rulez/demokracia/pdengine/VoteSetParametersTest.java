package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteSetParametersTest extends CreatedDefaultVoteRegistry {

	private Vote vote;
	boolean canAddin, canEndorse, canVote, canView;
	int minEndorsements;
	
	String originVoteId, originAdminKey;
	List<String> originNeededAssurances, originCountedAssurances;
	Boolean originIsPrivate;
	long originCreationTime;
	
	@Before
	public void setUp() throws ReportedException {
		super.setUp();
		vote = voteManager.getVote(adminInfo.voteId);
		
		originVoteId = vote.id;
		originAdminKey = vote.adminKey;
		originNeededAssurances = new ArrayList<String>(vote.neededAssurances);
		originCountedAssurances = new ArrayList<String>(vote.countedAssurances);
		originIsPrivate = vote.isPrivate;
		originCreationTime = vote.creationTime;
		
		minEndorsements = 0;
		canAddin = true;
		canEndorse = true;
		canVote = true;
		canView = true;
		voteManager.setVoteParameters(adminInfo.voteId, 
				adminInfo.adminKey,
				minEndorsements,
				canAddin,
				canEndorse,
				canVote,
				canView);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() {
		String voteName = "modifiedVoteName";
		assertThrows(
				() -> voteManager.setVoteParameters(voteName,
						adminInfo.adminKey,
						minEndorsements,
						canAddin,
						canEndorse,
						canVote,
						canView)
			).assertMessageIs(String.format("illegal voteId: %s", voteName));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
				() -> voteManager.setVoteParameters(adminInfo.voteId,
						invalidAdminKey,
						minEndorsements,
						canAddin,
						canEndorse,
						canVote,
						canView)
			).assertMessageIs(String.format("Illegal adminKey: %s", invalidAdminKey));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("validates inputs")
	@Test
	public void invalid_minEndorsements_is_rejected() {
		int invalidMinEndorsements = -2;
		assertThrows(
				() -> voteManager.setVoteParameters(adminInfo.voteId,
						adminInfo.adminKey,
						invalidMinEndorsements,
						canAddin,
						canEndorse,
						canVote,
						canView)
			).assertMessageIs(String.format("Illegal minEndorsements: %s", invalidMinEndorsements));
	}
	
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_minEndorsement_parameter_of_the_vote() {
		assertEquals(minEndorsements, vote.minEndorsements);
	}

	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_canAddIn_parameter_of_the_vote() {
		assertEquals(true, vote.canAddin);
	}

	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_canEndorse_parameter_of_the_vote() {
		assertEquals(true, vote.canEndorse);
	}

	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_canVote_parameter_of_the_vote() {
		assertEquals(true, vote.canVote);
	}

	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_canView_parameter_of_the_vote() {
		assertEquals(true, vote.canView);
	}
	
	
	
	
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_vote_id_value() {
		assertEquals(originVoteId, vote.id);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_admin_key_value() {
		assertEquals(originAdminKey, vote.adminKey);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_neededAssurances_value() {
		assertEquals(originNeededAssurances, vote.neededAssurances);
	}

	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_countedAssurances_value() {
		assertEquals(originCountedAssurances, vote.countedAssurances);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_isPrivate_value() {
		assertEquals(originIsPrivate, vote.isPrivate);
	}
	
	@tested_feature("Manage votes")
	@tested_operation("set vote parameters")
	@tested_behaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_creationTime_value() {
		assertEquals(originCreationTime, vote.creationTime);
	}
}
