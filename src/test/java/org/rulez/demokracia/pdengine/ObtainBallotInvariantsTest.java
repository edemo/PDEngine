package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

@TestedFeature("Manage votes")
@TestedOperation("Obtain ballot")
public class ObtainBallotInvariantsTest extends CreatedDefaultChoice {

  private String originalVoteId;
  private String originalAdminKey;
  private List<String> originalNeededAssurances;
  private List<String> originalCountedAssurances;
  private boolean originalIsPrivate;
  private boolean originalCanUpdate;
  private long originalCreationTime;

  @Before
  public void setUp() {
    super.setUp();
    originalVoteId = adminInfo.voteId;
    originalAdminKey = adminInfo.adminKey;
    vote = voteManager.getVote(originalVoteId);

    originalNeededAssurances = new ArrayList<>(vote.neededAssurances);
    originalCountedAssurances = new ArrayList<>(vote.countedAssurances);
    originalIsPrivate = vote.isPrivate;
    originalCreationTime = vote.creationTime;
    originalCanUpdate = vote.parameters.canUpdate;

    voteManager.obtainBallot(originalVoteId, originalAdminKey);
  }

  @TestedBehaviour("vote invariants")
  @Test
  public void voteId_is_Invariant() {
    assertEquals(originalVoteId, adminInfo.voteId);
  }

  @TestedBehaviour("vote invariants")
  @Test
  public void adminKey_is_Invariant() {
    assertEquals(originalAdminKey, vote.adminKey);
  }

  @TestedBehaviour("vote invariants")
  @Test
  public void neededAssurances_is_Invariant() {
    assertEquals(originalNeededAssurances, vote.neededAssurances);
  }

  @TestedBehaviour("vote invariants")
  @Test
  public void countedAssurances_is_Invariant() {
    assertEquals(originalCountedAssurances, vote.countedAssurances);
  }

  @TestedBehaviour("vote invariants")
  @Test
  public void isPrivate_is_Invariant() {
    assertEquals(originalIsPrivate, vote.isPrivate);
  }

  @TestedBehaviour("vote invariants")
  @Test
  public void creationTime_is_Invariant() {
    assertEquals(originalCreationTime, vote.creationTime);
  }

  @TestedBehaviour("updatable is a vote invariant")
  @Test
  public void canUpdate_is_Invariant() {
    assertEquals(originalCanUpdate, vote.parameters.canUpdate);
  }
}
