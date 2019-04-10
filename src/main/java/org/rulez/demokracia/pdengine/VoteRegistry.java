package org.rulez.demokracia.pdengine;

import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.exception.ReportedException;

import com.google.gson.JsonObject;

public class VoteRegistry extends ChoiceManager implements IVoteManager {

  public VoteRegistry(final WebServiceContext wsContext) {
    super(wsContext);
  }

  @Override
  public String obtainBallot(final String identifier, final String adminKey) {
    final Vote vote = getVote(identifier);
    vote.checkAdminKey(adminKey);

    if (adminKey.equals(vote.getAdminKey()))
      vote.increaseRecordedBallots("admin");
    else {
      if (getWsContext().getUserPrincipal() == null)
        throw new IllegalArgumentException(
            "Simple user is not authenticated, cannot issue any ballot."
        );
      if (!userHasAllAssurance(vote.getNeededAssurances()))
        throw new IllegalArgumentException(
            "The user does not have all of the needed assurances."
        );
      if (vote.getRecordedBallotsCount(getWsUserName()).intValue() > 0)
        throw new IllegalArgumentException("This user already have a ballot.");

      vote.increaseRecordedBallots(getWsUserName());
    }

    final String ballot = RandomUtils.createRandomKey();
    vote.addBallot(ballot);
    return ballot;
  }

  public boolean userHasAllAssurance(final List<String> neededAssuranceList) {
    for (final String neededAssurance : neededAssuranceList)
      if (!hasAssurance(neededAssurance))
        return false;
    return true;
  }

  @Override
  public CastVote castVote(
      final String voteId, final String ballot, final List<RankedChoice> theVote
  ) {
    final Vote vote = getVote(voteId);

    checkIfVotingEnabled(vote);
    checkIfUpdateConditionsAreConsistent(vote);
    validateBallot(ballot, vote);
    validatePreferences(theVote, vote);

    CastVote receipt;

    if (vote.getParameters().canUpdate)
      receipt = vote.addCastVote(getWsUserName(), theVote);
    else
      receipt = vote.addCastVote(null, theVote);

    vote.removeBallot(ballot);
    return receipt;
  }

  private void
      validatePreferences(final List<RankedChoice> theVote, final Vote vote) {
    for (final RankedChoice choice : theVote)
      validateOnePreference(vote, choice);
  }

  private void
      validateOnePreference(final Vote vote, final RankedChoice choice) {
    checkChoiceID(vote, choice);
    checkRank(choice);
  }

  private void checkRank(final RankedChoice choice) {
    if (choice.rank < 0)
      throw new ReportedException("Invalid rank");
  }

  private void checkChoiceID(final Vote vote, final RankedChoice choice) {
    if (!vote.getChoices().containsKey(choice.choiceId))
      throw new ReportedException("Invalid choiceId");
  }

  private void validateBallot(final String ballot, final Vote vote) {
    if (!vote.getBallots().contains(ballot))
      throw new ReportedException("Illegal ballot");
  }

  private void checkIfUpdateConditionsAreConsistent(final Vote vote) {
    if (
      vote.getParameters().canUpdate &&
          getWsContext().getUserPrincipal() == null
    )
      throw new ReportedException(
          "canUpdate is true but the user is not authenticated"
      );
  }

  private void checkIfVotingEnabled(final Vote vote) {
    if (!vote.parameters.canVote)
      throw new ReportedException("This issue cannot be voted on yet");
  }

  @Override
  public void
      modifyVote(final VoteAdminInfo voteAdminInfo, final String voteName) {
    ValidationUtil.checkVoteName(voteName);
    final Vote vote = checkIfVoteCanBeModified(voteAdminInfo);

    vote.name = voteName;
  }

  @Override
  public void deleteVote(final VoteAdminInfo adminInfo) {
    final Vote vote = checkIfVoteCanBeModified(adminInfo);

    session.remove(vote);
  }

  private Vote checkIfVoteCanBeModified(final VoteAdminInfo adminInfo) {
    final Vote vote = checkAdminInfo(adminInfo);

    if (vote.hasIssuedBallots())
      throw new IllegalArgumentException(
          "This vote cannot be modified it has issued ballots."
      );
    return vote;
  }

  private Vote checkAdminInfo(final VoteAdminInfo adminInfo) {
    final Vote vote = getVote(adminInfo.voteId);
    vote.checkAdminKey(adminInfo.adminKey);
    return vote;
  }

  @Override
  public JsonObject showVote(final VoteAdminInfo adminInfo) {
    final Vote vote = checkAdminInfo(adminInfo);
    if (!adminInfo.adminKey.equals(vote.adminKey))
      checkAssurances(vote);

    return vote.toJson();
  }

  private void checkAssurances(final Vote vote) {
    for (final String assurance : vote.countedAssurances)
      if (!hasAssurance(assurance))
        throw new ReportedException("missing assurances", assurance);
  }

  @Override
  public String
      deleteChoice(final VoteAdminInfo voteAdminInfo, final String choiceId) {
    final Vote vote =
        getVoteIfModifiable(voteAdminInfo.voteId, voteAdminInfo.adminKey);

    final Choice votesChoice = vote.getChoice(choiceId);
    if ("user".equals(voteAdminInfo.adminKey)) {
      checkUserName(votesChoice);
      checkCanAddin(vote);
      vote.choices.remove(votesChoice.id);
    } else
      vote.choices.remove(votesChoice.id);
    return "OK";
  }

  private void checkCanAddin(final Vote vote) {
    if (!vote.parameters.canAddin)
      throw new IllegalArgumentException(
          "Choice modification disallowed: adminKey is user, but canAddin is false"
      );
  }

  private void checkUserName(final Choice votesChoice) {
    if (!votesChoice.userName.equals(getWsUserName()))
      throw new IllegalArgumentException(
          "The adminKey is \"user\" but the user is not same with that user who added the choice."
      );
  }

  @Override
  public void modifyChoice(
      final VoteAdminInfo adminInfo, final String choiceId,
      final String choiceName
  ) {
    final Vote vote = getVoteIfModifiable(adminInfo.voteId, adminInfo.adminKey);

    final Choice votesChoice = vote.getChoice(choiceId);
    if ("user".equals(adminInfo.adminKey)) {
      checkCanAddin(vote);
      checkUserName(votesChoice);
    }

    votesChoice.name = choiceName;
  }

  @Override
  public void setVoteParameters(
      final VoteAdminInfo adminInfo, final VoteParameters voteParameters
  ) {
    final Vote vote = checkAdminInfo(adminInfo);

    if (voteParameters.minEndorsements >= 0)
      vote.setParameters(
          voteParameters.minEndorsements, voteParameters.canAddin,
          voteParameters.canEndorse,
          voteParameters.canVote, voteParameters.canView
      );
    else
      throw new ReportedException(
          "Illegal minEndorsements",
          Integer.toString(voteParameters.minEndorsements)
      );
  }
}
