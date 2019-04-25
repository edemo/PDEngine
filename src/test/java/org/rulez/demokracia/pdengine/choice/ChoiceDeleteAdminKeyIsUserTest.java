package org.rulez.demokracia.pdengine.choice;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;

@TestedFeature("Manage votes")
@TestedOperation("delete choice")
@RunWith(MockitoJUnitRunner.class)
public class ChoiceDeleteAdminKeyIsUserTest extends ChoiceTestBase {

  private static final String USER = "user";
  private static final String CHOICE1 = "choice1";
  private static final String TEST_USER_NAME = "teszt_elek";

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @TestedBehaviour(
    "if \"user\" is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  @Test
  public void if_canAddin_is_false_then_other_users_cannot_delete_choices() {
    final Choice choiceToDelete = createChoice(TEST_USER_NAME, false);
    when(authService.getAuthenticatedUserName()).thenReturn(TEST_USER_NAME);
    assertThrows(
        () -> choiceService.deleteChoice(new VoteAdminInfo(vote.getId(), USER), choiceToDelete.getId())
    ).assertMessageIs("The adminKey is \"user\" but canAddin is false.");
  }

  @TestedBehaviour(
    "if \"user\" is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  @Test
  public void
      if_adminKey_is_user_and_the_user_is_not_the_one_who_added_the_choice_then_the_choice_cannot_be_deleted() {
    final Choice choiceToDelete = createChoice(USER, true);
    assertThrows(
        () -> choiceService.deleteChoice(new VoteAdminInfo(vote.getId(), USER),
            choiceToDelete.getId()
        )
    ).assertMessageIs(
        "The adminKey is \"user\" but the user is not same with that user who added the choice."
    );
  }

  @TestedBehaviour(
    "if \"user\" is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  @Test
  public void
      if_adminKey_is_user_and_canAddin_is_true_then_the_user_who_added_the_choice_is_able_to_delete_it() {
    final Choice choiceToDelete = createChoice(TEST_USER_NAME, true);
    when(authService.getAuthenticatedUserName()).thenReturn(TEST_USER_NAME);
    choiceService.deleteChoice(
        new VoteAdminInfo(vote.getId(), USER), choiceToDelete.getId()
    );

    assertThrows(
        () -> choiceService.getChoice(
            vote.getId(),
            choiceToDelete.getId()
        )
    ).assertMessageIs("Illegal choiceId");
  }

  @TestedBehaviour(
    "if \"user\" is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  @Test
  public void deleteChoice_saves_vote_if_the_choice_is_deleted() {
    final Choice choiceToDelete = createChoice(TEST_USER_NAME, true);
    when(authService.getAuthenticatedUserName()).thenReturn(TEST_USER_NAME);
    choiceService.deleteChoice(
        new VoteAdminInfo(vote.getId(), USER), choiceToDelete.getId()
    );
    verify(voteService).saveVote(vote);
  }

  @TestedBehaviour(
    "if the vote has ballots issued, the choice cannot be deleted"
  )
  @Test
  public void
      if_the_vote_has_issued_ballots_then_the_choice_cannot_be_deleted() {
    final Choice choiceToDelete = createChoice(TEST_USER_NAME, true);
    vote.getBallots().add("TestBallot");

    assertThrows(
        () -> choiceService.deleteChoice(
            new VoteAdminInfo(
                vote.getId(),
                vote.getAdminKey()
            ), choiceToDelete.getId()
        )
    )
        .assertMessageIs("Vote modification disallowed: ballots already issued");
  }

  private Choice
      createChoice(final String userName, final boolean isAddinable) {
    vote.getParameters().setAddinable(isAddinable);
    final Choice choiceToDelete = new Choice(CHOICE1, userName);
    vote.addChoice(choiceToDelete);
    return choiceToDelete;
  }
}
