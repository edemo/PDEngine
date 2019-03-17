package org.rulez.demokracia.pdengine;

import java.util.List;
import java.util.Set;

import javax.xml.ws.WebServiceContext;

import org.json.JSONObject;
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
	
	String deleteChoice(String voteId, String choiceId, String adminKey) throws ReportedException;

	void modifyChoice(String voteId, String choiceId, String adminKey, String choice) throws ReportedException;

	ChoiceEntity getChoice(String voteId, String choiceId);

	void endorseChoice(String adminKey, String voteId, String choiceId, String statedUserName);

	String obtainBallot(String id, String adminKey);

	void castVote(String voteId, String ballot, List<RankedChoice> theVote);

	String getWsUserName();

	boolean hasAssurance(String role);

	void modifyVote(String voteId, String adminKey, String votename) throws ReportedException;

	void deleteVote(String voteId, String adminKey) throws ReportedException;
	
	JSONObject showVote(String voteId, String adminKey) throws ReportedException;

	void setVoteParameters(String voteId, String adminKey, int minEndorsements, boolean canAddin, boolean canEndorse,
			boolean canVote, boolean canView) throws ReportedException;

}