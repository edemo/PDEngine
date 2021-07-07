package org.rulez.demokracia.pdengine.choice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;

@TestedFeature("Manage votes")
@TestedOperation("delete choice")
@RunWith(MockitoJUnitRunner.class)
public class ChoiceDeleteValidatorServiceTest extends ChoiceValidatorBaseTest {

  @Test
  @TestedBehaviour(
    "if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  public void userAdmin_cannot_delete_choice_if_canAddin_is_false() {
    assertFalseCanAddinCauseException(
        () -> underTest.validateDeletion(getUserAdminInfo(), vote, choice)
    );
  }

  @TestedBehaviour("modifies the string of the choice")
  @Test
  public void proper_voteId_choiceId_and_adminKey_does_modify_choice() {
    assertNotThrows(
        () -> {
          underTest.validateDeletion(getAdminInfo(), vote, choice);
        }
    );
  }

  @TestedBehaviour(
    "if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  @Test
  public void
      userAdmin_cannot_modify_choice_if_it_is_not_added_by_other_user() {
    assertDifferentUserCauseException(
        () -> underTest.validateDeletion(getUserAdminInfo(), vote, choice)
    );
  }

  @TestedBehaviour(
    "if 'user' is used as adminKey, then the user must be the one who added the choice and canAddIn be true"
  )
  @Test
  public void
      userAdmin_can_modify_the_choice_if_canAddin_is_true_and_he_is_the_choice_creator() {
    assertNoExceptionWithCorrectParameters(
        () -> underTest.validateDeletion(getUserAdminInfo(), vote, choice2)
    );
  }

  @Override
  protected String getOperation() {
    return "deletion";
  }

}
