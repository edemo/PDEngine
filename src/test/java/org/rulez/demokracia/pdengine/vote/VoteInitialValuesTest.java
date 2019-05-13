package org.rulez.demokracia.pdengine.vote;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.VariantVote;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("The vote initially can only be modified with the adminkey")
public class VoteInitialValuesTest {

  @Test
  public void canAddin_is_false_in_the_created_vote() {
    assertEquals(false, getTheVote().getParameters().isAddinable());
  }

  @Test
  public void canEndorse_is_false_in_the_created_vote() {
    assertEquals(false, getTheVote().getParameters().isEndorsable());
  }

  @Test
  public void canVote_is_false_in_the_created_vote() {
    assertEquals(false, getTheVote().getParameters().isVotable());
  }

  @Test
  public void canView_is_false_in_the_created_vote() {
    assertEquals(false, getTheVote().getParameters().isViewable());
  }

  @Test
  public void canUpdate_is_false_in_the_created_vote() {
    assertEquals(false, getTheVote().getParameters().isUpdatable());
  }

  @Test
  public void minEndorsement_is_set_in_the_created_vote() {
    assertEquals(3, getTheVote().getParameters().getMinEndorsements());
  }

  private Vote getTheVote() {
    return new VariantVote();
  }
}
