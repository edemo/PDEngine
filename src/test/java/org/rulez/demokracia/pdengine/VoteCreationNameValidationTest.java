package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("formally validates all inputs")
public class VoteCreationNameValidationTest extends CreatedDefaultVoteRegistry {

  @Test
  public void vote_name_can_contain_spaces() {
    voteName = "This contains spaces";
    final VoteAdminInfo voteadm = createAVote();
    assertEquals(voteName, voteManager.getVote(voteadm.voteId).name);

  }

  @Test
  public void vote_name_cannot_be_null() {
    voteName = null;//NOPMD
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("vote name is null");
  }

  @Test
  public void vote_name_cannot_contain_tabs() {
    voteName = "thiscontainstab\t";
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("invalid characters in vote name");
  }

  @Test
  public void votename_max_length_is_255_characters() {
    final int length = 255;
    final String str255 = createLongString(length);
    voteName = str255;

    createAVote();
    voteName = str255 + "w";
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("string too long: vote name");
  }

  @Test
  public void minimum_vote_name_length_is_3() {
    voteName = "aaa";
    createAVote();
    voteName = "aa";
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("string too short: vote name");
  }

  @Test
  public void votename_can_contain_local_characters() {
    voteName = "ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ";

    final VoteAdminInfo secondVote = createAVote();

    assertEquals(voteName, voteManager.getVote(secondVote.voteId).name);

  }
}
