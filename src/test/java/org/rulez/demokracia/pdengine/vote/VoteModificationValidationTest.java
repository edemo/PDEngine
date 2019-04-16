package org.rulez.demokracia.pdengine.vote;

import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.testhelpers.ThrowableTester;

@TestedFeature("Manage votes")
@TestedOperation("modify vote")
@RunWith(MockitoJUnitRunner.class)
public class VoteModificationValidationTest extends ThrowableTester {

	private static final String NEW_VOTE_NAME = "newVoteName";

	@InjectMocks
	private VoteServiceImpl voteService;

	@Mock
	private VoteRepository voteRepository;

	private Vote existingVote;

	@Before
	public void setUp() {
		existingVote = new Vote("name", Set.of(), Set.of(), false, 1);
		when(voteRepository.findById(existingVote.getId())).thenReturn(Optional.of(existingVote));
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void the_name_is_verified_against_the_same_rules_as_in_vote_creation() {
		String modifiedVoteName = null;
		assertThrows(
				() -> voteService.modifyVote(
						new VoteAdminInfo(existingVote.getId(), existingVote.getAdminKey()), modifiedVoteName)
				).assertMessageIs("vote name is null");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
				() -> voteService.modifyVote(
						new VoteAdminInfo(existingVote.getId(), invalidAdminKey), NEW_VOTE_NAME)
				).assertMessageIs("Illegal adminKey");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() {
		String invalidvoteId = RandomUtils.createRandomKey();
		assertThrows(
				() -> voteService.modifyVote(
						new VoteAdminInfo(invalidvoteId, existingVote.getAdminKey()), NEW_VOTE_NAME)
				).assertMessageIs("illegal voteId");
	}

	@TestedBehaviour("The vote cannot be modified if there are ballots issued.")
	@Test
	public void modifyVote_with_ballot_get_an_exception() {
		existingVote.getBallots().add("TestBallots");

		assertThrows(
				() -> voteService.modifyVote(
						new VoteAdminInfo(existingVote.getId(), existingVote.getAdminKey()), NEW_VOTE_NAME)
				).assertMessageIs("This vote cannot be modified it has issued ballots.");
	}
}