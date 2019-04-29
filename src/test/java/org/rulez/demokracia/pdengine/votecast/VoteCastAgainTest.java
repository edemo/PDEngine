package org.rulez.demokracia.pdengine.votecast;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.rulez.demokracia.pdengine.testhelpers.CastVoteTestHelper.CHOICE_B;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.choice.RankedChoice;

@TestedFeature("Vote")
@TestedOperation("Cast vote")
@TestedBehaviour(
  "if there was a cast vote from the same user, the old one is deleted"
)
@RunWith(MockitoJUnitRunner.class)
public class VoteCastAgainTest extends CastVoteTestBase {

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @Test
  public void
      cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_top_of_the_list() {
    String userName = vote.getVotesCast().get(0).getProxyId();
    assertChangeVoteOfUser(userName, true);
  }

  @Test
  public void
      cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_bottom_of_the_list() {
    List<CastVote> votesCast = vote.getVotesCast();
    String userName = votesCast.get(votesCast.size() - 1).getProxyId();
    assertChangeVoteOfUser(userName, true);
  }

  @Test
  public void
      cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_middle_of_the_list() {
    String userName = vote.getVotesCast().get(1).getProxyId();
    assertChangeVoteOfUser(userName, true);
  }

  @Test
  public void
      cast_vote_records_the_vote_with_same_user_votesCast_when_the_list_does_not_contain_same_user() {
    assertChangeVoteOfUser("newUser", false);
  }

  private void
      assertChangeVoteOfUser(final String userName, final boolean hasVote) {
    when(authService.getAuthenticatedUserName()).thenReturn(userName);

    int expectedVoteNumber = vote.getVotesCast().size() + (hasVote ? 0 : 1);

    castVoteService.castVote(
        vote.getId(), ballot,
        List.of(new RankedChoice(CHOICE_B.getId(), 1))
    );

    assertEquals(CHOICE_B.getId(), getFirstChoiceIdOfUser(userName));
    assertEquals(expectedVoteNumber, vote.getVotesCast().size());
  }

  private String getFirstChoiceIdOfUser(final String user) {
    return vote.getVotesCast().stream().filter(c -> user.equals(c.getProxyId()))
        .findFirst()
        .get().getPreferences().get(0).getChoiceId();
  }
}
