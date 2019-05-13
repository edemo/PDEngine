package org.rulez.demokracia.pdengine.showvote;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import com.google.gson.JsonObject;

public interface ShowVoteService {

  JsonObject showVote(VoteAdminInfo adminInfo);

}
