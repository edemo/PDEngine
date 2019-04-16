package org.rulez.demokracia.pdengine;

import java.util.List;
import javax.xml.ws.WebServiceContext;

import org.rulez.demokracia.pdengine.dataobjects.ChoiceEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.vote.Vote;

import com.google.gson.JsonObject;

public interface IVoteManager {

	static IVoteManager getVoteManager(final WebServiceContext wsContext) {
		return VoteManagerUtils.getVoteManager(wsContext);
	}

	WebServiceContext getWsContext();

	Vote getVote(final String voteId);

	String addChoice(final VoteAdminInfo voteAdminInfo, final String choiceName, final String user);

	String deleteChoice(final VoteAdminInfo voteAdminInfo, final String choiceId);

	void modifyChoice(final VoteAdminInfo adminInfo, final String choiceId, final String choiceName);

	ChoiceEntity getChoice(final String voteId, final String choiceId);

	void endorseChoice(final VoteAdminInfo voteAdminInfo, final String choiceId, final String statedUserName);

	String obtainBallot(final String identifier, final String adminKey);

	CastVote castVote(final String voteId, final String ballot, final List<RankedChoice> theVote);

	String getWsUserName();

	boolean hasAssurance(final String role);

	JsonObject showVote(final VoteAdminInfo adminInfo);

}