package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("formally validates all inputs")
public class VoteCreationNeededAssurancesValidationTest
    extends CreatedDefaultVoteRegistry {

  @Test
  public void
      neededAssurances_is_checked_not_to_contain_strings_longer_than_255() {
    int length = 255;
    String str255 = createLongString(length);

    neededAssurances.add(str255);

    createAVote();
    String str256 = str255 + "w";
    neededAssurances.add(str256);
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("string too long: needed assurance name");
  }

  @Test
  public void
      neededAssurances_is_checked_not_to_contain_strings_shorter_than_3() {
    neededAssurances.add("aaa");

    createAVote();
    neededAssurances.add("aa");
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("string too short: needed assurance name");
  }

  @Test
  public void needed_assurances_should_not_contain_space() {
    neededAssurances.add("This contains space");

    assertThrows(
        () -> createAVote()
    ).assertMessageIs("invalid characters in needed assurance name");
  }

  @Test
  public void needed_assurances_should_not_contain_tab() {
    neededAssurances.add("thiscontainstab\t");
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("invalid characters in needed assurance name");
  }

  @Test
  public void needed_assurances_should_not_contain_empty_string() {
    neededAssurances.add("");
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("string too short: needed assurance name");
  }

  @Test
  public void needed_assurances_should_not_be_null() {
    neededAssurances.add(null);
    assertThrows(
        () -> createAVote()
    ).assertMessageIs("needed assurance name is null");
  }

  @Test
  public void needed_assurances_can_contain_local_characters() {
    neededAssurances.add("ThisConatinsLocaCharséűáőúöüóíÉÁŰŐÚÖÜÓÍ");

    createAVote();
    assertEquals(voteName, voteManager.getVote(adminInfo.voteId).name);

  }
}
