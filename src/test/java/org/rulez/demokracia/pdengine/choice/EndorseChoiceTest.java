package org.rulez.demokracia.pdengine.choice;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;

@TestedFeature("Vote")
@TestedOperation("Endorse option")
@RunWith(MockitoJUnitRunner.class)
public class EndorseChoiceTest extends ChoiceTestBase {

	private static final String TEST_USER_NAME = "testuserke";
	private Choice choice;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		choice = new Choice("name", "O1G");
		vote.addChoice(choice);
	}

	@TestedBehaviour("if adminKey is not user, the userName is registered "
			+ "as endorserName for the choice")
	@Test
	public void endorsement_is_registered() {
		choiceService.endorseChoice(new VoteAdminInfo(vote.getId(), vote.getAdminKey()), choice.getId(), "testuser");
		assertTrue(choice.getEndorsers().contains("testuser"));
	}

	@TestedBehaviour("if adminKey is 'user', then canEndorse must be true,"
			+ " and the proxy id of the user will be registered as endorserName for the choice")
	@Test
	public void if_adminKey_is_user_and_canEndorse_is_false_then_an_exception_is_thrown() {
		assertThrows(() -> choiceService.endorseChoice(
				new VoteAdminInfo(vote.getId(), "user"), choice.getId(), TEST_USER_NAME))
		.assertException(ReportedException.class);
	}

	@TestedBehaviour("if adminKey is 'user', then canEndorse must be true,"
			+ " and the proxy id of the user will be registered as endorserName for the choice")
	@Test
	public void if_adminKey_is_user_then_the_proxy_id_is_registered_for_the_vote() {
		vote.getParameters().setEndorsable(true);
		when(authService.getAuthenticatedUserName()).thenReturn(TEST_USER_NAME);
		choiceService.endorseChoice(new VoteAdminInfo(vote.getId(), "user"),
				choice.getId(), TEST_USER_NAME);
		assertTrue(choice.getEndorsers().contains(TEST_USER_NAME));
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void voteId_is_the_id_of_an_existing_vote() {
		doThrow(new RuntimeException("Illegal voteId")).when(voteService).getVote(invalidvoteId);
		assertThrows(() -> choiceService.endorseChoice(
				new VoteAdminInfo(invalidvoteId, "user"), choice.getId(), TEST_USER_NAME))
		.assertMessageIs("Illegal voteId");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		assertThrows(() -> choiceService.endorseChoice(
				new VoteAdminInfo(vote.getId(), "illegal"), choice.getId(), TEST_USER_NAME))
		.assertMessageIs("Illegal adminKey");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_choiceId_is_rejected() {
		assertThrows(() -> choiceService.endorseChoice(
				new VoteAdminInfo(vote.getId(), vote.getAdminKey()), "illegalChoice", TEST_USER_NAME))
		.assertMessageIs("Illegal choiceId");
	}
}
