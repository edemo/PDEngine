package org.rulez.demokracia.pdengine.votecast.validation;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static org.rulez.demokracia.pdengine.votecast.validation.VoteCastValidationTestHelper.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.votecast.CastVoteTestBase;

@TestedFeature("Vote")
@TestedOperation("Cast vote")
@RunWith(MockitoJUnitRunner.class)
public class VoteCastValidationTest extends CastVoteTestBase {

  private static final String VALIDATES_INPUTS = "validates inputs";
  private static final int LAST_WRONG_RANK = -1;
  private static final int FIRST_GOOD_RANK = 0;
  private List<RankedChoice> theCastVote;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    theCastVote = prepareCastVote(vote, true);
  }

  @TestedBehaviour("deletes the ballot with ballotId, so only one vote is possible with a ballot")
  @Test
  public void cast_vote_deletes_ballot_if_canVote_is_true() {
    castVoteService.castVote(vote.getId(), ballot, theCastVote);
    assertFalse(vote.getBallots().contains(ballot));
  }

  @TestedBehaviour("deletes the ballot with ballotId, so only one vote is possible with a ballot")
  @Test
  public void cast_vote_deletes_ballot_if_it_gets_a_proper_not_empty_cast_vote() {
    newChoiceAndRankedList(theCastVote, FIRST_GOOD_RANK, vote);

    castVoteService.castVote(vote.getId(), ballot, theCastVote);
    assertFalse(vote.getBallots().contains(ballot));
  }

  @TestedBehaviour("works only if canVote is true")
  @Test
  public void cast_vote_does_not_delete_ballot_if_canVote_is_false() {
    final List<RankedChoice> theCastVote = prepareCastVote(vote, false);

    assertCastVoteException(theCastVote, vote.getId(), ballot)
        .assertException(ReportedException.class)
        .assertMessageIs("This issue cannot be voted on yet");
  }

  @TestedBehaviour(VALIDATES_INPUTS)
  @Test
  public void cast_vote_checks_vote_id() {
    final String wrongVoteId = "wrong_ID";
    doThrow(new ReportedException("illegal voteId")).when(voteService).getVote(wrongVoteId);

    assertCastVoteException(theCastVote, wrongVoteId, ballot)
        .assertException(ReportedException.class).assertMessageIs("illegal voteId");
  }

  @TestedBehaviour(VALIDATES_INPUTS)
  @Test
  public void cast_vote_checks_ballot() {
    final String wrongBallot = RandomUtils.createRandomKey();

    assertCastVoteException(theCastVote, vote.getId(), wrongBallot)
        .assertException(ReportedException.class).assertMessageIs("Illegal ballot");
  }

  @TestedBehaviour(VALIDATES_INPUTS)
  @Test
  public void cast_vote_checks_the_cast_if_choiceids_are_valid() {
    prepareRankedChoice(theCastVote, RandomUtils.createRandomKey(), 42);

    assertCastVoteException(theCastVote, vote.getId(), ballot)
        .assertException(ReportedException.class).assertMessageIs("Invalid choiceId");
  }

  @TestedBehaviour(VALIDATES_INPUTS)
  @Test
  public void cast_vote_checks_the_cast_if_ranks_are_nonnegative() {

    newChoiceAndRankedList(theCastVote, LAST_WRONG_RANK, vote);

    assertCastVoteException(theCastVote, vote.getId(), ballot)
        .assertException(ReportedException.class).assertMessageIs("Invalid rank");
  }

  @TestedBehaviour("if updatable is true, only authenticated users can vote")
  @Test
  public void cannot_cast_a_user_if_canUpdate_is_true_but_not_logged_in() {
    vote.getParameters().setUpdatable(true);
    when(authService.getUserPrincipal()).thenReturn(null);

    newChoiceAndRankedList(theCastVote, FIRST_GOOD_RANK, vote);

    assertCastVoteException(theCastVote, vote.getId(), ballot)
        .assertException(ReportedException.class)
        .assertMessageIs("canUpdate is true but the user is not authenticated");

  }

  private ThrowableTester assertCastVoteException(final List<RankedChoice> theCastVote,
      final String voteId, final String ballot) {
    return assertThrows(() -> castVoteService.castVote(voteId, ballot, theCastVote));
  }
}
