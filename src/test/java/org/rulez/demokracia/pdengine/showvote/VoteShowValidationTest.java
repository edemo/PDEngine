package org.rulez.demokracia.pdengine.showvote;

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
@TestedOperation("show vote")
@TestedBehaviour("validates inputs")
@RunWith(MockitoJUnitRunner.class)
public class VoteShowValidationTest extends ShowVoteTestBase {

	private VoteAdminInfo invalidIdAdminInfo = new VoteAdminInfo("invalid", "user");
	private VoteAdminInfo invalidAdminKeyAdminInfo = new VoteAdminInfo("valid", "invalid");

	@Before
	public void setUp() {
		doThrow(new ReportedException("illegal voteId", "invalid"))
		.when(voteService).getVoteWithValidatedAdminKey(invalidIdAdminInfo);
		doThrow(new ReportedException("Illegal adminKey", "invalid")).when(voteService)
		.getVoteWithValidatedAdminKey(invalidAdminKeyAdminInfo);

	}

	@Test
	public void invalid_voteId_is_rejected() {
		assertThrows(
				() -> showVoteService.showVote(invalidIdAdminInfo)
				).assertMessageIs("illegal voteId");
	}

	@Test
	public void invalid_adminKey_is_rejected() {
		assertThrows(
				() -> showVoteService.showVote(invalidAdminKeyAdminInfo)).assertMessageIs("Illegal adminKey");
	}
}