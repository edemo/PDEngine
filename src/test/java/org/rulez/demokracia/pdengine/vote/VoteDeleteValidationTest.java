package org.rulez.demokracia.pdengine.vote;

import static org.mockito.Mockito.verify;
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
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.vote.Vote;

@TestedFeature("Manage votes")
@TestedOperation("delete vote")
@RunWith(MockitoJUnitRunner.class)
public class VoteDeleteValidationTest extends ThrowableTester {

	private static final String INVALID_VOTE_ID = "InvalidVoteId";

	@InjectMocks
	private VoteServiceImpl voteService;

	@Mock
	private VoteRepository voteRepository;
	private Vote existingVote;

	private String validVoteId;

	@Before
	public void setUp() {
		existingVote = new Vote("name", Set.of(), Set.of(), false, 1);
		validVoteId = existingVote.getId();
		when(voteRepository.findById(INVALID_VOTE_ID)).thenReturn(Optional.empty());
		when(voteRepository.findById(validVoteId)).thenReturn(Optional.of(existingVote));
	}

	@TestedBehaviour("validate inputs")
	@Test
	public void invalid_voteId_is_rejected() {
		assertThrows(
				() -> voteService.deleteVote(new VoteAdminInfo(INVALID_VOTE_ID, "ADMIN"))
				).assertMessageIs("illegal voteId");
	}


	@TestedBehaviour("validate inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		String invalidAdminKey = RandomUtils.createRandomKey();
		assertThrows(
				() -> voteService.deleteVote(new VoteAdminInfo(validVoteId, invalidAdminKey))
				).assertMessageIs("Illegal adminKey");
	}

	@TestedBehaviour("deletes the vote with all parameters, choices, ballots and votes cast")
	@Test
	public void proper_voteId_and_adminKey_with_ballot_does_not_delete_vote() {
		existingVote.getBallots().add("TestBallot");
		assertThrows(
				() -> voteService.deleteVote(new VoteAdminInfo(validVoteId, existingVote.getAdminKey()))
				).assertMessageIs("This vote cannot be modified it has issued ballots.");
	}

	@TestedBehaviour("deletes the vote with all parameters, choices, ballots and votes cast")
	@Test
	public void proper_voteId_and_adminKey_does_delete_vote() {
		voteService.deleteVote(new VoteAdminInfo(validVoteId, existingVote.getAdminKey()));
		verify(voteRepository).delete(existingVote);
	}
}