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

  @Autowired
  private ChoiceModificationValidatorService choiceModificationValidatorService;

  @Autowired
  private VoteService voteService;

  @Autowired
  private AuthenticatedUserService authenticatedUserService;

  @Override
  public Choice addChoice(
      final VoteAdminInfo voteAdminInfo, final String choiceName,
      final String user
  ) {
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
  public void endorseChoice(
      final VoteAdminInfo voteAdminInfo, final String choiceId,
      final String givenUserName
  ) {
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
  public void
      deleteChoice(final VoteAdminInfo voteAdminInfo, final String choiceId) {
    final Vote vote = getModifiableVote(voteAdminInfo, voteService);
    final Choice votesChoice = getChoice(voteAdminInfo.getVoteId(), choiceId);

    choiceModificationValidatorService
        .validateDeletion(voteAdminInfo, vote, votesChoice);

    vote.getChoices().remove(votesChoice.getId());

    voteService.saveVote(vote);
  }

  @Override
  public void modifyChoice(
      final VoteAdminInfo adminInfo, final String choiceId,
      final String choiceName
  ) {
    final Vote vote = getModifiableVote(adminInfo, voteService);

    final Choice votesChoice = vote.getChoice(choiceId);
    choiceModificationValidatorService
        .validateModification(adminInfo, vote, votesChoice);

    votesChoice.setName(choiceName);
    voteService.saveVote(vote);
  }

  private String getUserName() {
    return authenticatedUserService.getAuthenticatedUserName();
  }

  private void checkIfVoteIsEndorseable(final Vote vote) {
    if (!vote.isEndorsable())
      throw new ReportedException("user cannot endorse this vote");
  }

}
