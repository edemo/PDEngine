package org.rulez.demokracia.pdengine;

import java.util.List;

import javax.xml.ws.WebServiceContext;

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
			throw new IllegalArgumentException("This issue cannot be voted on on yet");
	}

	@Override
	public void modifyVote(String voteId, String adminKey, String votename) throws ReportedException {
	    Validate.checkVoteName(votename);
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		vote.name = votename;
	}
	
	public void deleteVote(String voteId, String adminKey) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		session.remove(vote);
	}
	
	public void modifyChoice(String voteId, String choiceId, String adminKey, String choice) throws ReportedException {
		Vote vote = getVote(voteId);
		vote.checkAdminKey(adminKey);
		Choice votesChoice = vote.getChoice(choiceId);
		
		if(!vote.hasIssuedBallots())
			votesChoice.name = choice;
	}
}
