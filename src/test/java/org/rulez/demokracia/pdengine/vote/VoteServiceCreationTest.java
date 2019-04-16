package org.rulez.demokracia.pdengine.vote;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("Creates a vote")
@RunWith(MockitoJUnitRunner.class)
public class VoteServiceCreationTest {

	private static final String VOTE_NAME = "vote name";

	private static final String ASSURANCE_NAME = "MyNameIsAssurance";

	@Mock
	private VoteRepository voteRepository;

	@InjectMocks
	private VoteServiceImpl voteService;

	private CreateVoteRequest createVoteRequest;

	private ArgumentCaptor<Vote> voteCaptor;

	@Before
	public void setUp() {
		createVoteRequest = new CreateVoteRequest();
		createVoteRequest.setVoteName(VOTE_NAME);
		createVoteRequest.setCountedAssurances(Set.of("Ass", "Urance", ASSURANCE_NAME));
		createVoteRequest.setNeededAssurances(Set.of("Akarmi", "Barmi", ASSURANCE_NAME));
	}

	@Test
	public void create_creates_a_vote_with_the_given_name() {
		callCreateVote();
		assertEquals(VOTE_NAME, voteCaptor.getValue().getName());
	}

	@Test
	public void create_vote_returns_correct_admin_info() {
		VoteAdminInfo createVote = callCreateVote();
		Vote savedVote = voteCaptor.getValue();
		assertEquals(createVote, new VoteAdminInfo(savedVote.getId(), savedVote.getAdminKey()));
	}

	@Test
	public void neededAssurances_contains_the_assurances_of_the_input() {
		callCreateVote();
		assertTrue(voteCaptor.getValue().getNeededAssurances().containsAll(createVoteRequest.getNeededAssurances()));
	}

	@Test
	public void countedAssurances_contains_the_assurances_of_the_input() {
		callCreateVote();
		assertTrue(voteCaptor.getValue().getCountedAssurances().containsAll(createVoteRequest.getCountedAssurances()));
	}

	@Test
	public void create_creates_a_vote_with_isPrivate() {
		callCreateVote();
		assertEquals(createVoteRequest.isPrivate(), voteCaptor.getValue().isPrivate());
	}

	@Test
	public void create_creates_a_vote_with_creationTime() {
		Instant before = Instant.now();
		callCreateVote();
		Instant after = Instant.now();
		long creationTime = voteCaptor.getValue().getCreationTime();
		assertBetweenInstants(creationTime, before, after);
	}

	@Test
	public void create_creates_a_vote_with_minEndorsements() {
		callCreateVote();
		assertEquals(createVoteRequest.getMinEndorsements(),
				voteCaptor.getValue().getParameters().getMinEndorsements());
	}

	private VoteAdminInfo callCreateVote() {
		voteCaptor = ArgumentCaptor.forClass(Vote.class);
		VoteAdminInfo voteAdminInfo = voteService.createVote(createVoteRequest);
		verify(voteRepository).save(voteCaptor.capture());
		return voteAdminInfo;
	}

	private void assertBetweenInstants(final long creationTime, final Instant before, final Instant after) {
		assertTrue(creationTime >= before.getEpochSecond());
		assertTrue(creationTime <= after.getEpochSecond());
	}
}
