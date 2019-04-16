package org.rulez.demokracia.pdengine;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("show vote")
@TestedBehaviour("validates inputs")
public class VoteShowValidationTest extends CreatedDefaultVoteRegistry {

  @Test
  public void invalid_voteId_is_rejected() {
    String invalidvoteId = RandomUtils.createRandomKey();
    assertThrows(
        () -> voteManager
            .showVote(new VoteAdminInfo(invalidvoteId, adminInfo.adminKey))
    ).assertMessageIs("illegal voteId");
  }

  @Test
  public void invalid_adminKey_is_rejected() {
    String invalidAdminKey = RandomUtils.createRandomKey();
    assertThrows(
        () -> voteManager
            .showVote(new VoteAdminInfo(adminInfo.voteId, invalidAdminKey))
    ).assertMessageIs("Illegal adminKey");
  }
}
