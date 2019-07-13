package org.rulez.demokracia.pdengine.choice;

import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.testhelpers.Thrower;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;
import org.rulez.demokracia.pdengine.vote.Vote;

public abstract class ChoiceValidatorBaseTest extends ThrowableTester {

  private static final String CHOICE_ADDED_BY_DIFFERENT_USER_MESSAGE =
      "Choice %s disallowed: adminKey is user, and the choice was added by a different user";
  private static final String CAN_ADDIN_IS_FALSE_MESSAGE =
      "Choice %s disallowed: adminKey is user, but canAddin is false";
  private static final String USER = "user";
  private static final String ADMIN = "admin";

  protected final Choice choice = new Choice("choiceName", USER);
  protected Vote vote = new VariantVote();

  @InjectMocks
  protected ChoiceModificationValidatorServiceImpl underTest;

  @Mock
  protected AuthenticatedUserService authenticatedUserService;

  protected Choice choice2 = new Choice("choice2", ADMIN);

  protected abstract String getOperation();

  protected String getDifferentUserMessage() {
    return String
        .format(CHOICE_ADDED_BY_DIFFERENT_USER_MESSAGE, getOperation());
  }

  protected String getCanAddinIsFalseMessage() {
    return String.format(CAN_ADDIN_IS_FALSE_MESSAGE, getOperation());
  }

  protected void assertDifferentUserCauseException(final Thrower thrower) {
    vote.getParameters().setAddinable(true);
    assertThrows(thrower).assertMessageIs(getDifferentUserMessage());
  }

  protected void assertNoExceptionWithCorrectParameters(final Thrower thrower) {
    vote.getParameters().setAddinable(true);
    vote.addChoice(choice2);
    when(authenticatedUserService.getAuthenticatedUserName()).thenReturn(ADMIN);

    assertNotThrows(thrower);
  }

  protected void assertFalseCanAddinCauseException(final Thrower thrower) {
    assertThrows(thrower).assertMessageIs(getCanAddinIsFalseMessage());
  }

  protected VoteAdminInfo getUserAdminInfo() {
    return new VoteAdminInfo(vote.getId(), USER);
  }

  protected VoteAdminInfo getAdminInfo() {
    return new VoteAdminInfo(vote.getId(), vote.getAdminKey());
  }

}
