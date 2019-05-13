package org.rulez.demokracia.pdengine.vote;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.ThrowableTester;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("vote invariants")
public class VoteInvariantTest extends ThrowableTester {

  private static final String BAD = "bad";
  private VariantVote vote;
  private final List<String> badAssurances = List.of(BAD);

  @Before
  public void setUp() {
    vote = new VariantVote();
  }

  @Test
  public void vote_id_is_invariant() {
    assertUnimplemented(() -> vote.setId(BAD));
  }

  @Test
  public void adminKey_is_invariant() {
    assertUnimplemented(() -> vote.setAdminKey(BAD));
  }

  @Test
  public void neededAssurances_is_invariant() {
    assertUnimplemented(() -> vote.setNeededAssurances(badAssurances));
  }

  @Test
  public void countedAssurances_is_invariant() {
    assertUnimplemented(() -> vote.setCountedAssurances(badAssurances));
  }

  @Test
  public void isPrivate_is_invariant() {
    assertUnimplemented(() -> vote.setPrivate(!vote.isPrivate()));

  }

  @Test
  public void creationTime_is_invariant() {
    assertUnimplemented(() -> vote.setCreationTime(0));
  }

}
