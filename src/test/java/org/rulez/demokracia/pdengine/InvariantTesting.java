package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class InvariantTesting extends CreatedDefaultVoteRegistry {

  public VoteEntity savedVote;
  public String savedVoteId;
  public String savedAdminKey;
  public List<String> savedNeededAssurances;
  public List<String> savedCountedAssurances;
  public boolean savedIsPrivate;
  public boolean savedCanUpdate;
  public long savedCreationTime;

  protected void saveInvariables(final VoteEntity vote) {
    savedVote = vote;
    savedVoteId = vote.id;
    savedAdminKey = vote.adminKey;
    savedNeededAssurances = new ArrayList<>(vote.neededAssurances);
    savedCountedAssurances = new ArrayList<>(vote.countedAssurances);
    savedIsPrivate = vote.isPrivate;
    savedCreationTime = vote.creationTime;
    savedCanUpdate = vote.parameters.canUpdate;
  }

  public void assertInvariables(final Vote vote) {
    assertEquals(savedVoteId, vote.id);
    assertEquals(savedAdminKey, vote.adminKey);
    assertEquals(savedNeededAssurances, vote.neededAssurances);
    assertEquals(savedCountedAssurances, vote.countedAssurances);
    assertEquals(savedIsPrivate, vote.isPrivate);
    assertEquals(savedCreationTime, vote.creationTime);
    assertEquals(savedCanUpdate, vote.parameters.canUpdate);
  }

}
