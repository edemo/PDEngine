package org.rulez.demokracia.pdengine.choice;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;
import org.rulez.demokracia.pdengine.vote.Vote;

@TestedFeature("Manage votes")
@TestedOperation("delete choice")
@RunWith(MockitoJUnitRunner.class)
public class ChoiceDeleteValidatorServiceTest extends ThrowableTester {

  private static final String CHOICE_ADDED_BY_DIFFERENT_USER_MESSAGE =
      "Choice deletion disallowed: adminKey is user, " +
          "and the choice was added by a different user";
  private static final String CAN_ADDIN_IS_FALSE_MESSAGE =
      "Choice deletion disallowed: adminKey is user, but canAddin is false";
  private static final String USER = "user";
  private static final String ADMIN = "admin";

  private final Choice choice = new Choice("choiceName", USER);
  protected Vote vote = new VariantVote();

  @InjectMocks
  private ChoiceModificationValidatorServiceImpl underTest;

  @Mock
  private AuthenticatedUserService authenticatedUserService;

  @Test
  @TestedBehaviour(
    "if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  public void userAdmin_cannot_delete_choice_if_canAddin_is_false() {
    assertThrows(
        () -> underTest
            .validateDeletion(new VoteAdminInfo(vote.getId(), USER),
                vote, choice
            )
    ).assertMessageIs(CAN_ADDIN_IS_FALSE_MESSAGE);
  }

  @TestedBehaviour("modifies the string of the choice")
  @Test
  public void proper_voteId_choiceId_and_adminKey_does_modify_choice() {
    underTest.validateDeletion(
        new VoteAdminInfo(vote.getId(), vote.getAdminKey()), vote, choice
    );
  }

  @TestedBehaviour(
    "if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  @Test
  public void
      userAdmin_cannot_modify_choice_if_it_is_not_added_by_other_user() {
    vote.getParameters().setAddinable(true);

    assertThrows(
        () -> underTest
            .validateDeletion(new VoteAdminInfo(vote.getId(), USER), vote,
                choice
            )
    )
        .assertMessageIs(
            CHOICE_ADDED_BY_DIFFERENT_USER_MESSAGE
        );
  }

  @TestedBehaviour(
    "if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  @Test
  public void
      userAdmin_can_modify_the_choice_if_canAddin_is_true_and_he_is_the_choice_creator() {
    vote.getParameters().setAddinable(true);
    final Choice choice = new Choice("choice2", ADMIN);
    vote.addChoice(choice);
    when(authenticatedUserService.getAuthenticatedUserName()).thenReturn(ADMIN);
    underTest
        .validateDeletion(new VoteAdminInfo(vote.getId(), USER), vote, choice);
  }
}
