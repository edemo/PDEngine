package org.rulez.demokracia.pdengine.choice;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.vote.VoteService;

@TestedFeature("Manage votes")
@TestedOperation("Add choice")
@RunWith(MockitoJUnitRunner.class)
public class ChoiceAddValidationTest extends ThrowableTester {

  @InjectMocks
  private ChoiceServiceImpl choiceService;

  @Mock
  private VoteService voteService;

  private final Vote vote = new VariantVote();
  private final VoteAdminInfo adminInfo = new VoteAdminInfo(vote.getId(), vote.getAdminKey());
  private final String invalidvoteId = RandomUtils.createRandomKey();

  @Before
  public void setUp() {
    when(voteService.getVote(adminInfo.getVoteId())).thenReturn(vote);
    doThrow(new ReportedException("illegal voteId", invalidvoteId)).when(voteService)
        .getVote(invalidvoteId);
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_voteId_is_rejected() {
    assertaddChoiceThrowsUp(invalidvoteId, vote.getAdminKey()).assertMessageIs("illegal voteId");
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_adminKey_is_rejected() {
    assertaddChoiceThrowsUp(vote.getId(), "invalidAdminKey").assertMessageIs("Illegal adminKey");
  }

  @TestedBehaviour("No choice can be added if there are ballots issued for the vote.")
  @Test
  public void no_choice_can_be_added_there_are_issued_ballots() {
    vote.getBallots().add("TestBallots");
    assertaddChoiceThrowsUp(vote.getId(), vote.getAdminKey())
        .assertMessageIs("Vote modification disallowed: ballots already issued");
  }

  private ThrowableTester assertaddChoiceThrowsUp(final String voteId, final String adminKey) {
    return assertThrows(() -> {
      choiceService.addChoice(new VoteAdminInfo(voteId, adminKey), "choice1", "user");
    });
  }
}
