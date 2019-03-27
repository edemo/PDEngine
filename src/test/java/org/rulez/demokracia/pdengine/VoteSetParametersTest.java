package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("set vote parameters")
public class VoteSetParametersTest extends CreatedDefaultVoteRegistry {

	private Vote vote;
	private String originVoteId;
	private String originAdminKey;
	private List<String> originNeededAssurances;
	private List<String> originCountedAssurances;
	private Boolean originIsPrivate;
	private Boolean originCanUpdate;
	private long originCreationTime;
	private VoteParameters voteParameters;

	@Before
	@Override
	public void setUp() {
		super.setUp();
		vote = voteManager.getVote(adminInfo.voteId);

		originVoteId = vote.id;
		originAdminKey = vote.adminKey;
		originNeededAssurances = new ArrayList<>(vote.neededAssurances);
		originCountedAssurances = new ArrayList<>(vote.countedAssurances);
		originIsPrivate = vote.isPrivate;
		originCreationTime = vote.creationTime;
		originCanUpdate = vote.parameters.canUpdate;

		voteParameters = new VoteParameters();
		voteParameters.minEndorsements = 0;
		voteParameters.canAddin = true;
		voteParameters.canEndorse = true;
		voteParameters.canVote = true;
		voteParameters.canView = true;

		voteManager.setVoteParameters(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey),voteParameters);
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() {
		String voteName = "modifiedVoteName";
		assertThrows(
				() -> {
					voteManager.setVoteParameters(new VoteAdminInfo(voteName, adminInfo.adminKey), voteParameters);
				}
				).assertMessageIs("illegal voteId");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
				() -> voteManager.setVoteParameters(new VoteAdminInfo(adminInfo.voteId, invalidAdminKey), voteParameters)
				).assertMessageIs("Illegal adminKey");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_minEndorsements_is_rejected() {
		int invalidMinEndorsements = -2;
		voteParameters.minEndorsements = invalidMinEndorsements;
		assertThrows(
				() -> voteManager.setVoteParameters(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), voteParameters)
				).assertMessageIs("Illegal minEndorsements");
	}

	@TestedBehaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_minEndorsement_parameter_of_the_vote() {
		assertEquals(minEndorsements, vote.parameters.minEndorsements);
	}

	@TestedBehaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_canAddIn_parameter_of_the_vote() {
		assertEquals(true, vote.parameters.canAddin);
	}

	@TestedBehaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_canEndorse_parameter_of_the_vote() {
		assertEquals(true, vote.parameters.canEndorse);
	}

	@TestedBehaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_canVote_parameter_of_the_vote() {
		assertEquals(true, vote.parameters.canVote);
	}

	@TestedBehaviour("sets the parameters of the vote")
	@Test
	public void setVoteParameters_sets_the_canView_parameter_of_the_vote() {
		assertEquals(true, vote.parameters.canView);
	}

	@TestedBehaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_vote_id_value() {
		assertEquals(originVoteId, vote.id);
	}

	@TestedBehaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_admin_key_value() {
		assertEquals(originAdminKey, vote.adminKey);
	}

	@TestedBehaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_neededAssurances_value() {
		assertEquals(originNeededAssurances, vote.neededAssurances);
	}

	@TestedBehaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_countedAssurances_value() {
		assertEquals(originCountedAssurances, vote.countedAssurances);
	}

	@TestedBehaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_isPrivate_value() {
		assertEquals(originIsPrivate, vote.isPrivate);
	}

	@TestedBehaviour("vote invariants")
	@Test
	public void setVoteParameters_does_not_overwrite_creationTime_value() {
		assertEquals(originCreationTime, vote.creationTime);
	}

	@TestedBehaviour("updatable is a vote invariant")
	@Test
	public void setVoteParameters_does_not_overwrite_canUpdate_value() {
		assertEquals(originCanUpdate, vote.parameters.canUpdate);
	}
}
