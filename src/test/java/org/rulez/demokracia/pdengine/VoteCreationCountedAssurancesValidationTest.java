package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("formally validates all inputs")
public class VoteCreationCountedAssurancesValidationTest
    extends CreatedDefaultVoteRegistry {

  @Test
  public void
      countedAssurances_is_checked_not_to_contain_strings_longer_than_255() {
    int length = 255;
    String str255 = createLongString(length);

    countedAssurances.add(str255);

    createAVote();
    String str256 = str255 + "w";
    countedAssurances.add(str256);
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("string too long: counted assurance name");
  }

  @Test
  public void
      countedAssurances_is_checked_not_to_contain_strings_shorter_than_3() {
    countedAssurances.add("aaa");

    createAVote();
    countedAssurances.add("aa");
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("string too short: counted assurance name");
  }

  @Test
  public void counted_assurances_should_not_contain_space() {
    countedAssurances.add("This contains space");

    assertThrows(
        () -> createAVote()
    ).assertMessageIs("invalid characters in counted assurance name");
  }

  @Test
  public void counted_assurances_should_not_contain_tab() {
    countedAssurances.add("thiscontainstab\t");
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("invalid characters in counted assurance name");
  }

  @Test
  public void counted_assurances_can_contain_local_characters() {
    countedAssurances.add("ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ");

    createAVote();
    assertEquals(voteName, voteManager.getVote(adminInfo.voteId).name);

  }

  @Test
  public void counted_assurance_can_be_empty() {
    countedAssurances.add("");
    VoteAdminInfo secondVote = createAVote();
    assertTrue(
        voteManager.getVote(secondVote.voteId).countedAssurances.contains(null)
    );
  }
}
