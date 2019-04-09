package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("delete choice")
public class ChoiceDeleteValidationTest extends CreatedDefaultVoteRegistry {

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_voteId_is_rejected() {
    String invalidvoteId = RandomUtils.createRandomKey();
    String choiceId = voteManager.addChoice(
        new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choice1", "user"
    );
    assertThrows(
        () -> voteManager.deleteChoice(
            new VoteAdminInfo(invalidvoteId, adminInfo.adminKey), choiceId
        )
    ).assertMessageIs("illegal voteId");
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_choiceId_is_rejected() {
    String invalidChoiceId = "InvalidChoiceId";
    assertThrows(
        () -> voteManager
            .deleteChoice(new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), invalidChoiceId)
    ).assertMessageIs("Illegal choiceId");
  }

  @TestedBehaviour("validates inputs")
  @Test
  public void invalid_adminKey_is_rejected() {
    String invalidAdminKey = RandomUtils.createRandomKey();
    String choiceId = voteManager.addChoice(
        new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choice1", "user"
    );
    assertThrows(
        () -> voteManager.deleteChoice(
            new VoteAdminInfo(adminInfo.voteId, invalidAdminKey), choiceId
        )
    ).assertMessageIs("Illegal adminKey");
  }

  @TestedBehaviour("deletes the choice")
  @Test
  public void proper_voteId_choiceId_and_adminKey_does_delete_choice() {
    String choiceId = voteManager.addChoice(
        new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), "choice1", "user"
    );
    String voteId = adminInfo.voteId;
    voteManager.deleteChoice(
        new VoteAdminInfo(adminInfo.voteId, adminInfo.adminKey), choiceId
    );
    assertThrows(
        () -> voteManager.getChoice(voteId, choiceId)
    ).assertMessageIs("Illegal choiceId");
  }
}
