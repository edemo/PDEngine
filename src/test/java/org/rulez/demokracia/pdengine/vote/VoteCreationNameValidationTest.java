package org.rulez.demokracia.pdengine.vote;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

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
import org.rulez.demokracia.pdengine.testhelpers.LongStringGenerator;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("formally validates all inputs")
@RunWith(MockitoJUnitRunner.class)
public class VoteCreationNameValidationTest extends ThrowableTester {

	@InjectMocks
	private VoteServiceImpl voteService;

	@Mock
	private VoteRepository voteRepository;

	private CreateVoteRequest request;

	private ArgumentCaptor<Vote> voteCaptor = ArgumentCaptor.forClass(Vote.class);

	private String voteName;

	@Before
	public void setUp() {
		request = new CreateVoteRequest();
		request.setCountedAssurances(Set.of());
		request.setNeededAssurances(Set.of());
	}
	@Test
	public void vote_name_can_contain_spaces() {
		voteName = "This contains spaces";
		createAVote(voteName);
		verify(voteRepository).save(voteCaptor.capture());
		assertEquals(voteName, voteCaptor.getValue().getName());
	}

	@Test
	public void vote_name_cannot_be_null() {
		assertThrows(
				() -> createAVote(null)
				).assertMessageIs("vote name is null");
	}

	@Test
	public void vote_name_cannot_contain_tabs() {
		voteName = "thiscontainstab\t";
		assertThrows(
				() -> createAVote(voteName)
				).assertMessageIs("invalid characters in vote name");
	}

	@Test
	public void votename_max_length_is_255_characters() {
		String str255 = LongStringGenerator.generate(255);

		createAVote(str255);
		String voteName = str255 + "w";
		assertThrows(
				() -> createAVote(voteName)
				).assertMessageIs("string too long: vote name");
	}

	@Test
	public void minimum_vote_name_length_is_3() {
		voteName = "aaa";
		createAVote(voteName);
		voteName = "aa";
		assertThrows(
				() -> createAVote(voteName)
				).assertMessageIs("string too short: vote name");
	}

	@Test
	public void votename_can_contain_local_characters() {
		voteName = "ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ";

		createAVote(voteName);
		verify(voteRepository).save(voteCaptor.capture());
		assertEquals(voteName, voteCaptor.getValue().getName());
	}

	private VoteAdminInfo createAVote(final String voteName) {
		request.setVoteName(voteName);
		return voteService.createVote(request);
	}
}