package org.rulez.demokracia.pdengine.showvote;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;
import com.google.gson.JsonObject;

@TestedFeature("Manage votes")
@TestedOperation("show vote")
@TestedBehaviour("if adminKey is anon, the user should have any of the countedAssurances")
@RunWith(MockitoJUnitRunner.class)
public class ForShowVoteTheUserNeedsACountedAssuranceTest extends ShowVoteTestBase {

	@Before
	public void setUp() {
		when(authService.hasAssurance("german")).thenReturn(false);
		when(authService.hasAssurance("magyar")).thenReturn(true);
	}

	@Test
	public void a_user_with_not_all_assourances_cannot_show_the_vote() throws ReportedException {
		assertAssurancesMissing(createVoteWithCountedAssurances(List.of("german")));
	}

	@Test
	public void a_user_with_not_all_assourances_cannot_show_the_vote_even_with_more_assurances() throws ReportedException {
		assertAssurancesMissing(createVoteWithCountedAssurances(List.of("magyar", "german")));
	}

	@Test
	public void a_user_with_all_assourances_can_show_the_vote() throws ReportedException {
		Vote vote = createVoteWithCountedAssurances(List.of("magyar"));
		VoteAdminInfo aminInfo = new VoteAdminInfo(vote.getId(), "user");
		JsonObject voteJson = showVoteService.showVote(aminInfo);

		assertNotNull(voteJson);
	}

	private void assertAssurancesMissing(final Vote vote) {
		VoteAdminInfo aminInfo = new VoteAdminInfo(vote.getId(), "user");

		assertThrows(
				() -> showVoteService.showVote(aminInfo)
				).assertMessageIs("missing assurances");
	}

	private Vote createVoteWithCountedAssurances(final List<String> assurances) {
		Vote vote = new Vote("name", List.of(), assurances, false, 1);
		when(voteService.getVoteWithValidatedAdminKey(any())).thenReturn(vote);
		return vote;
	}
}
