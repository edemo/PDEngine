package org.rulez.demokracia.pdengine;

import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.json.JSONObject;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public class VoteRegistry extends ChoiceManager implements IVoteManager {
	public VoteRegistry(WebServiceContext wsContext) {
		super(wsContext);
	}
	
	@Override
	public String obtainBallot(String id, String adminKey) {
		Vote vote = getVote(id);
		vote.checkAdminKey(adminKey);
		String ballot = RandomUtils.createRandomKey();
		vote.ballots.add(ballot);
		return ballot;
	}

	@Override
	public void castVote(String voteId, String ballot, List<RankedChoice> theVote) {
		Vote vote = getVote(voteId);
		if(vote.canVote)
		  vote.ballots.remove(ballot);
		else
			throw new IllegalArgumentException("This issue cannot be voted on yet");
	}

	@Override
	public void modifyVote(String voteId, String adminKey, String votename) throws ReportedException {
	    Validate.checkVoteName(votename);
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		
		if(vote.hasIssuedBallots())
			throw new IllegalArgumentException("The vote cannot be modified there are ballots issued.");
		
		vote.name = votename;
	}
	
	public void deleteVote(String voteId, String adminKey) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		
		if(vote.hasIssuedBallots())
			throw new IllegalArgumentException("This vote cannot be deleted it has issued ballots.");
		
		session.remove(vote);
	}
	
	public JSONObject showVote(String voteId, String adminKey) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		
		return vote.toJson(voteId);
	}

	@Override
	public void deleteChoice(String voteId, String choiceId, String adminKey) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		Choice votesChoice = vote.getChoice(choiceId);
		
		if(adminKey.equals("user"))
			if(votesChoice.userName.equals(getWsUserName()))
				if(vote.canAddin)  
					vote.choices.remove(votesChoice.id);
				else
					throw new IllegalArgumentException("The adminKey is \"user\" but canAddin is false.");	  
			else
				throw new IllegalArgumentException("The adminKey is \"user\" but the user is not same with that user who added the choice.");
		else
			vote.choices.remove(votesChoice.id);
	}
	
	public void modifyChoice(String voteId, String choiceId, String adminKey, String choice) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		Choice votesChoice = vote.getChoice(choiceId);
		
		if(vote.hasIssuedBallots())
			throw new IllegalArgumentException("The vote have issued ballots so it can not be modified");
			
		votesChoice.name = choice;
	}

	@Override
	public void setVoteParameters(String voteId, String adminKey, int minEndorsements, boolean canAddin,
			boolean canEndorse, boolean canVote, boolean canView) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		
		if(minEndorsements >= 0)
			vote.setParameters(adminKey, minEndorsements, canAddin, canEndorse, canVote, canView);	
		else
			throw new IllegalArgumentException(String.format("Illegal minEndorsements: %s", minEndorsements));
	}
}
