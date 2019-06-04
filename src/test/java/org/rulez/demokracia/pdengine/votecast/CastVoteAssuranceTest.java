package org.rulez.demokracia.pdengine.votecast;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.rulez.demokracia.pdengine.testhelpers.CastVoteTestHelper.RANKED_CHOICES;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;

@TestedFeature("Supporting functionality")
@TestedOperation("CastVote")
@RunWith(MockitoJUnitRunner.class)
public class CastVoteAssuranceTest extends CastVoteTestBase {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    when(authService.getAuthenticatedUserName()).thenReturn(USER_NAME);
    when(assuranceManager.getAssurances(USER_NAME))
        .thenReturn(ASSURANCES);
  }

  @TestedBehaviour(
    "The assurances of the voter can be obtained from a cast vote if canupdateis true"
  )
  @Test
  public void
      the_assurances_of_the_voter_can_be_obtained_from_a_cast_vote_if_canupdate_is_true() {
    vote.getParameters().setUpdatable(true);
    final CastVote castVote = castVoteService
        .castVote(vote.getId(), ballot, RANKED_CHOICES);
    assertEquals(ASSURANCES, castVote.getAssurances());
  }

  @TestedBehaviour(
    "The assurances of the voter can be obtained from a cast vote if canupdateis true"
  )
  @Test
  public void the_assurances_of_the_voter_is_null_if_canupdate_is_false() {
    vote.getParameters().setUpdatable(false);
    final CastVote castVote = castVoteService
        .castVote(vote.getId(), ballot, RANKED_CHOICES);
    assertTrue(Objects.isNull(castVote.getAssurances()));
  }
}
