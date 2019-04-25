package org.rulez.demokracia.pdengine.choice;

import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;

@TestedFeature("Manage votes")
@TestedOperation("delete choice")
@RunWith(MockitoJUnitRunner.class)
public class ChoiceDeleteValidationTest extends ChoiceTestBase {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_voteId_is_rejected() {
		doThrow(new ReportedException("illegal voteId",
				invalidvoteId)).when(voteService).getVote(invalidvoteId);
		assertThrows(
				() -> choiceService.deleteChoice(new VoteAdminInfo(invalidvoteId, vote.getAdminKey()), "choiceId")
				).assertMessageIs("illegal voteId");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_choiceId_is_rejected() {
		String invalidChoiceId = "InvalidChoiceId";
		assertThrows(
				() -> choiceService.deleteChoice(new VoteAdminInfo(vote.getId(),
						vote.getAdminKey()), invalidChoiceId)).assertMessageIs("Illegal choiceId");
	}

	@TestedBehaviour("validates inputs")
	@Test
	public void invalid_adminKey_is_rejected() {
		Choice choice = new Choice("ChoiceName", "user");
		vote.addChoice(choice);
		String invalidAdminKey = "InvalidAdminKey";
		assertThrows(
				() -> choiceService.deleteChoice(new VoteAdminInfo(vote.getId(),
						invalidAdminKey), choice.getId())).assertMessageIs("Illegal adminKey");
	}
}