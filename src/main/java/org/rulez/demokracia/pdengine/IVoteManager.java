package org.rulez.demokracia.pdengine;

import java.util.List;
import java.util.Set;

import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;

public interface IVoteManager {

	static IVoteManager getVoteManager(WebServiceContext wsContext) {
		return VoteManagerRegistry.getVoteManager(wsContext);
	}

	WebServiceContext getWsContext();

	VoteAdminInfo createVote(String voteName, Set<String> neededAssurances, Set<String> countedAssurances,
			boolean isPrivate, int minEndorsements) throws ReportedException;

	Vote getVote(String voteId);

	String addChoice(String adminKey, String voteId, String choiceName, String user);

	ChoiceEntity getChoice(String voteId, String choiceId);

	void endorseChoice(String adminKey, String voteId, String choiceId, String statedUserName);

	String obtainBallot(String id, String adminKey);

	void castVote(String voteId, String ballot, List<RankedChoice> theVote);

	String getWsUserName();

	boolean hasAssurance(String role);

	void modifyVote(String voteId, String adminKey, String votename) throws ReportedException;

}