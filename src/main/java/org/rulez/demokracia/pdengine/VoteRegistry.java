package org.rulez.demokracia.pdengine;

import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.json.JSONObject;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class VoteRegistry extends ChoiceManager implements IVoteManager {
	public VoteRegistry(final WebServiceContext wsContext) {
		super(wsContext);
	}

	@Override
	public String obtainBallot(final String identifier, final String adminKey) {
		Vote vote = getVote(identifier);
		vote.checkAdminKey(adminKey);

		if (adminKey.equals(vote.adminKey))
			vote.increaseRecordedBallots("admin");

		else if ("user".equals(adminKey)) {
			if (getWsContext().getUserPrincipal() == null)
				throw new IllegalArgumentException("Simple user is not authenticated, cannot issue any ballot.");
			if (!userHasAllAssurance(vote.neededAssurances))
				throw new IllegalArgumentException("The user does not have all of the needed assurances.");
			if (vote.getRecordedBallotsCount(getWsUserName()).intValue() > 0)
				throw new IllegalArgumentException("This user already have a ballot.");

			vote.increaseRecordedBallots(getWsUserName());
			}

		String ballot = RandomUtils.createRandomKey();
		vote.ballots.add(ballot);
		return ballot;
	}

	public boolean userHasAllAssurance(final List<String> neededAssuranceList) {
		for (String neededAssurance : neededAssuranceList) {
			if (!hasAssurance(neededAssurance)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void castVote(final String voteId, final String ballot, final List<RankedChoice> theVote) {
		Vote vote = getVote(voteId);
		if (!vote.canVote)
			throw new ReportedException("This issue cannot be voted on yet");

		if (vote.canUpdate && getWsContext().getUserPrincipal() == null)
			throw new ReportedException("canUpdate is true but the user is not authenticated");

		if (!vote.ballots.contains(ballot))
			throw new ReportedException("Illegal ballot");

		for (RankedChoice choice : theVote) {
			if (!vote.choices.containsKey(choice.choiceId))
				throw new ReportedException("Invalid choiceId");
			if (choice.rank < 0)
				throw new ReportedException("Invalid rank");
		}
    
		if (vote.canUpdate)
			vote.addCastVote(getWsUserName(), theVote);
		else
			vote.addCastVote(null, theVote);
		
		vote.ballots.remove(ballot);
	}

	@Override
	public void modifyVote(final String voteId, final String adminKey, final String votename) {
		ValidationUtil.checkVoteName(votename);
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);

		if (vote.hasIssuedBallots())
			throw new IllegalArgumentException("The vote cannot be modified there are ballots issued.");

		vote.name = votename;
	}

	public void deleteVote(final String voteId, final String adminKey) {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);

		if (vote.hasIssuedBallots())
			throw new IllegalArgumentException("This vote cannot be deleted it has issued ballots.");

		session.remove(vote);
	}

	public JSONObject showVote(final String voteId, final String adminKey) {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);

		return vote.toJson(voteId);
	}

	@Override
	public String deleteChoice(final String voteId, final String choiceId, final String adminKey) {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);

		if (vote.hasIssuedBallots())
			throw new IllegalArgumentException("This choice cannot be deleted the vote has issued ballots.");

		Choice votesChoice = vote.getChoice(choiceId);
		if ("user".equals(adminKey))
			if (votesChoice.userName.equals(getWsUserName()))
				if (vote.canAddin)
					vote.choices.remove(votesChoice.id);
				else
					throw new IllegalArgumentException("The adminKey is \"user\" but canAddin is false.");
			else
				throw new IllegalArgumentException(
						"The adminKey is \"user\" but the user is not same with that user who added the choice.");
		else
			vote.choices.remove(votesChoice.id);
		return "OK";
	}

	public void modifyChoice(final String voteId, final String choiceId, final String adminKey, final String choice) {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);

		if (vote.hasIssuedBallots())
			throw new ReportedException("Choice modification disallowed: ballots already issued");

		Choice votesChoice = vote.getChoice(choiceId);
		if ("user".equals(adminKey)) {
			if (!vote.canAddin)
				throw new ReportedException(
						"Choice modification disallowed: adminKey is user, but canAddin is false");

			if (!votesChoice.userName.equals(getWsUserName()))
				throw new ReportedException(
						"Choice modification disallowed: adminKey is user, "
								+ "and the choice was added by a different user",
						votesChoice.userName);
		}

		votesChoice.name = choice;
	}

	@Override
	public void setVoteParameters(final String voteId, final String adminKey, final int minEndorsements, final boolean canAddin,
			final boolean canEndorse, final boolean canVote, final boolean canView) {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);

		if (minEndorsements >= 0)
			vote.setParameters(minEndorsements, canAddin, canEndorse, canVote, canView);
		else
			throw new IllegalArgumentException(String.format("Illegal minEndorsements: %s", minEndorsements));
	}
}
