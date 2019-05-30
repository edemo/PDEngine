package org.rulez.demokracia.pdengine.choice;

import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoiceServiceImpl implements ChoiceService {

  private static final String USER = "user";

  private static final String MODIFY_NOT_SAME_USER_MESSAGE =
      "Choice modification disallowed: adminKey is user, "
          + "and the choice was added by a different user";

  private static final String MODIFY_CANT_ADDIN_MESSAGE =
      "Choice modification disallowed: adminKey is user, but canAddin is false";

  private static final String DELETE_CANT_ADDIN = "The adminKey is \"user\" but canAddin is false.";

  private static final String DELETE_NOT_SAME_USER_MESSAGE =
      "The adminKey is \"user\" but the user is not same with that user who added the choice.";

  @Autowired
  private VoteService voteService;

  @Autowired
  private AuthenticatedUserService authenticatedUserService;

  @Override
  public Choice addChoice(final VoteAdminInfo voteAdminInfo, final String choiceName,
      final String user) {
    final Vote vote = getModifiableVote(voteAdminInfo, voteService);
    final Choice choice = new Choice(choiceName, user);
    vote.addChoice(choice);
    voteService.saveVote(vote);
    return choice;
  }

  @Override
  public Choice getChoice(final String voteId, final String choiceId) {
    return voteService.getVote(voteId).getChoice(choiceId);
  }

  @Override
  public void endorseChoice(final VoteAdminInfo voteAdminInfo, final String choiceId,
      final String givenUserName) {
    String userName = givenUserName;
    final Vote vote = voteService.getVote(voteAdminInfo.voteId);
    if (USER.equals(voteAdminInfo.adminKey)) {
      checkIfVoteIsEndorseable(vote);
      userName = getUserName();
    }
    vote.checkAdminKey(voteAdminInfo.adminKey);
    vote.getChoice(choiceId).endorse(userName);
  }

  @Override
  public void deleteChoice(final VoteAdminInfo voteAdminInfo, final String choiceId) {
    final Vote vote = getModifiableVote(voteAdminInfo, voteService);
    final Choice votesChoice = getChoice(voteAdminInfo.getVoteId(), choiceId);

    if (voteAdminInfo.isUserAdminKey())
      deleteChoiceAsUser(vote, votesChoice);
    else
      vote.getChoices().remove(votesChoice.getId());

    voteService.saveVote(vote);
  }

  @Override
  public void modifyChoice(final VoteAdminInfo adminInfo, final String choiceId,
      final String choiceName) {
    final Vote vote = getModifiableVote(adminInfo, voteService);

    final Choice votesChoice = vote.getChoice(choiceId);
    validateModifyInput(adminInfo, vote, votesChoice);

    votesChoice.setName(choiceName);
    voteService.saveVote(vote);
  }

  private void validateModifyInput(final VoteAdminInfo adminInfo, final Vote vote,
      final Choice votesChoice) {
    if (!USER.equals(adminInfo.adminKey))
      return;
    checkIfVoteIsAddinable(vote, new ReportedException(MODIFY_CANT_ADDIN_MESSAGE));
    checkIfUserIsTheSame(votesChoice, getUserName(),
        new ReportedException(MODIFY_NOT_SAME_USER_MESSAGE, votesChoice.getUserName()));
  }

  private String getUserName() {
    return authenticatedUserService.getAuthenticatedUserName();
  }

  private void checkIfVoteIsEndorseable(final Vote vote) {
    if (!vote.isEndorsable())
      throw new ReportedException("user cannot endorse this vote");
  }

  private void deleteChoiceAsUser(final Vote vote, final Choice votesChoice) {
    checkIfUserIsTheSame(votesChoice, getUserName(),
        new IllegalArgumentException(DELETE_NOT_SAME_USER_MESSAGE));
    checkIfVoteIsAddinable(vote, new IllegalArgumentException(DELETE_CANT_ADDIN));
    vote.getChoices().remove(votesChoice.getId());
  }

}
