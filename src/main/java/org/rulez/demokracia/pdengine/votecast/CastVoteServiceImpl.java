package org.rulez.demokracia.pdengine.votecast;

import static org.rulez.demokracia.pdengine.votecast.validation.CastVotePreferencesValidatorUtil.validatePreferences;
import static org.rulez.demokracia.pdengine.votecast.validation.CastVoteValidationUtil.*;

import java.util.List;
import java.util.Objects;

import org.rulez.demokracia.pdengine.assurance.AssuranceManager;
import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CastVoteServiceImpl implements CastVoteService {

  @Autowired
  private AuthenticatedUserService authService;

  @Autowired
  private VoteService voteService;

  @Autowired
  private AssuranceManager assuranceManager;

  @Override
  public CastVote castVote(
      final String voteId, final String ballot,
      final List<RankedChoice> rankedChoices
  ) {
    final Vote vote = voteService.getVote(voteId);
    validateInput(ballot, rankedChoices, vote);
    vote.removeBallot(ballot);

    final CastVote result = addCastVote(rankedChoices, vote);
    voteService.saveVote(vote);
    return result;
  }

  private CastVote
      addCastVote(final List<RankedChoice> rankedChoices, final Vote vote) {
    final String proxyId = vote.getParameters().isUpdatable() ?
        authService.getAuthenticatedUserName() : null;

    if (Objects.nonNull(proxyId))
      vote.getVotesCast()
          .removeIf(castVote -> proxyId.equals(castVote.getProxyId()));

    final CastVote castVote = createCastVote(rankedChoices, proxyId);
    vote.getVotesCast().add(castVote);
    return castVote;
  }

  private CastVote
      createCastVote(
          final List<RankedChoice> rankedChoices, final String proxyId
      ) {
    return Objects.isNull(proxyId) ? new CastVote(proxyId, rankedChoices) :
        new CastVote(
            proxyId, rankedChoices, assuranceManager.getAssurances(proxyId)
        );
  }

  private void validateInput(
      final String ballot, final List<RankedChoice> rankedChoices,
      final Vote vote
  ) {
    checkIfVotingEnabled(vote);
    checkIfUpdateConditionsAreConsistent(vote, authService);
    validateBallot(ballot, vote);
    validatePreferences(rankedChoices, vote);
  }
}
