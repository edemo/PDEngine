package org.rulez.demokracia.pdengine.showvote;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.choice.Choice;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.vote.Vote;

import com.google.gson.JsonObject;

public class ShowVoteJSONTestBase extends ShowVoteTestBase {

  private static final String USER = "user";
  private static final String CHOICE_NAME = "choiceName";

  protected Vote vote;
  protected String voteId;
  protected String adminKey;
  protected JsonObject result;

  @Before
  public void setUp() {
    vote = new Vote(
        "votename", List.of("needed1", "needed2"), List.of("counted1", "counted2"), true, 3
    );
    vote.addChoice(new Choice(CHOICE_NAME, USER));
    voteId = vote.getId();
    adminKey = vote.getAdminKey();

    when(voteService.getVoteWithValidatedAdminKey(any())).thenReturn(vote);

    result = createJson();
  }

  protected JsonObject createJson() {
    return showVoteService.showVote(
        new VoteAdminInfo(
            voteId,
            adminKey
        )
    );
  }
}
