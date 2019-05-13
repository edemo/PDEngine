package org.rulez.demokracia.pdengine.votecast;

import static org.junit.Assert.assertEquals;
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
@TestedBehaviour("The vote receipt contains the ballot cast and the cast vote identifier")
@RunWith(MockitoJUnitRunner.class)
public class VoteCastReceiptTest extends CastVoteTestBase {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    vote.getVotesCast().clear();
  }

  @Test
  public void cast_vote_returns_the_cast_vote_id() {
    CastVote receipt = castVoteService.castVote(vote.getId(), ballot,
        List.of(new RankedChoice(CHOICE_B.getId(), 1)));
    assertEquals(vote.getVotesCast().get(0).getSecretId(), receipt.getSecretId());
  }

  @Test
  public void cast_vote_returns_the_cast_vote_preferences() {
    CastVote receipt = castVoteService.castVote(vote.getId(), ballot,
        List.of(new RankedChoice(CHOICE_B.getId(), 1)));
    assertEquals(vote.getVotesCast().get(0).getPreferences().get(0).getChoiceId(),
        receipt.getPreferences().get(0).getChoiceId());
  }

}
