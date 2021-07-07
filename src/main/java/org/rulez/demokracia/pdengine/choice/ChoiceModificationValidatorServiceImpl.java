package org.rulez.demokracia.pdengine.choice;

import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoiceModificationValidatorServiceImpl
    implements ChoiceModificationValidatorService {

  private static final String MODIFICATION = "modification";
  private static final String DELETION = "deletion";
  private static final String CAN_ADDIN_IS_FALSE_MESSAGE =
      "Choice %s disallowed: adminKey is user, but canAddin is false";
  private static final String DIFFERENT_USER_MESSAGE =
      "Choice %s disallowed: adminKey is user, and the choice was added by a different user";
  @Autowired
  private AuthenticatedUserService authenticationService;

  @Override
  public void validateModification(
      final VoteAdminInfo voteAdminInfo, final Vote vote, final Choice choice
  ) {
    validate(voteAdminInfo, vote, choice, MODIFICATION);
  }

  @Override
  public void validateDeletion(
      final VoteAdminInfo voteAdminInfo, final Vote vote, final Choice choice
  ) {
    validate(voteAdminInfo, vote, choice, DELETION);
  }

  private void validate(
      final VoteAdminInfo voteAdminInfo,
      final Vote vote,
      final Choice choice,
      final String operation
  ) {
    if (voteAdminInfo.isUserAdminKey()) {
      validateAddinability(vote, operation);
      validateMatchingUser(choice, operation);
    }
  }

  private void
      validateMatchingUser(final Choice choice, final String operation) {
    if (!choice.getUserName().equals(getUserName()))
      throw new ReportedException(
          String.format(DIFFERENT_USER_MESSAGE, operation)
      );
  }

  private void validateAddinability(final Vote vote, final String operation) {
    if (!vote.getParameters().isAddinable())
      throw new ReportedException(
          String.format(CAN_ADDIN_IS_FALSE_MESSAGE, operation)
      );
  }

  private String getUserName() {
    return authenticationService.getAuthenticatedUserName();
  }

}
