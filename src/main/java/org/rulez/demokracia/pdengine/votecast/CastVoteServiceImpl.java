package org.rulez.demokracia.pdengine.votecast;

import java.util.List;
import java.util.Objects;

import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.exception.ReportedException;
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

	@Override
	public CastVote castVote(final String voteId, final String ballot, final List<RankedChoice> rankedChoices) {
		Vote vote = voteService.getVote(voteId);
		validateInput(ballot, rankedChoices, vote);
		vote.removeBallot(ballot);

		CastVote result = addCastVote(rankedChoices, vote);
		voteService.saveVote(vote);
		return result;
	}

	private CastVote addCastVote(final List<RankedChoice> rankedChoices, final Vote vote) {
		String proxyId = vote.getParameters().isUpdatable() ? authService.getAuthenticatedUserName() : null;

		if (Objects.nonNull(proxyId)) {
			vote.getVotesCast().removeIf(castVote -> proxyId.equals(castVote.getProxyId()));
		}

		CastVote castVote = new CastVote(proxyId, rankedChoices);
		vote.getVotesCast().add(castVote);
		return castVote;
	}

	private void validateInput(final String ballot, final List<RankedChoice> rankedChoices, final Vote vote) {
		checkIfVotingEnabled(vote);
		checkIfUpdateConditionsAreConsistent(vote);
		validateBallot(ballot, vote);
		validatePreferences(rankedChoices, vote);
	}

	private void validatePreferences(final List<RankedChoice> rankedChoices, final Vote vote) {
		rankedChoices.forEach(choice -> validateOnePreference(vote, choice));
	}

	private void validateOnePreference(final Vote vote, final RankedChoice choice) {
		if (!vote.getChoices().containsKey(choice.getChoiceId())) {
			throw new ReportedException("Invalid choiceId");
		}
		if (choice.getRank() < 0) {
			throw new ReportedException("Invalid rank");
		}
	}

	private void validateBallot(final String ballot, final Vote vote) {
		if (!vote.getBallots().contains(ballot)) {
			throw new ReportedException("Illegal ballot");
		}
	}

	private void checkIfVotingEnabled(final Vote vote) {
		if (!vote.getParameters().isVotable()) {
			throw new ReportedException("This issue cannot be voted on yet");
		}
	}

	private void checkIfUpdateConditionsAreConsistent(final Vote vote) {
		if (vote.getParameters().isUpdatable() && authService.getUserPrincipal() == null) {
			throw new ReportedException("canUpdate is true but the user is not authenticated");
		}
	}
}
