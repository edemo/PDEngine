package org.rulez.demokracia.pdengine.choice;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;

public interface ChoiceService {

	Choice addChoice(VoteAdminInfo voteAdminInfo, String choiceName, String user);

	Choice getChoice(String voteId, String choiceId);

	void endorseChoice(VoteAdminInfo voteAdminInfo, String choiceId, String givenUserName);

	void deleteChoice(VoteAdminInfo voteAdminInfo, String choiceId);

	void modifyChoice(VoteAdminInfo adminInfo, String choiceId, String choiceName);

}
