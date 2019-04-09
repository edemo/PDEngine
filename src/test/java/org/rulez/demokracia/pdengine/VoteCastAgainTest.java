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

@TestedFeature("Vote")
@TestedOperation("Cast vote")
@TestedBehaviour(
  "if there was a cast vote from the same user, the old one is deleted"
)
public class VoteCastAgainTest extends CreatedDefaultChoice {

  @Before
  public void setUp() {
    super.setUp();
    initializeVoteCastTest();
  }

  @Test
  public void
      cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_top_of_the_list() {
    vote.parameters.canUpdate = true;

    addCastVote(TEST_USER_NAME, theCastVote);
    addfirstDummies();
    addSecondDummies();

    voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

    assertTrue(vote.votesCast.get(7).proxyId.equals(TEST_USER_NAME));
  }

  @Test
  public void
      cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_bottom_of_the_list() {
    List<RankedChoice> theCastVote1 = new ArrayList<>();
    vote.parameters.canUpdate = true;

    addfirstDummies();
    addSecondDummies();
    addCastVote(TEST_USER_NAME, theCastVote1);

    voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

    assertTrue(vote.votesCast.get(7).preferences.equals(theCastVote1));
  }

  @Test
  public void
      cast_vote_records_the_vote_with_same_user_votesCast_when_same_user_is_in_the_middle_of_the_list() {
    vote.parameters.canUpdate = true;

    addfirstDummies();
    addCastVote(TEST_USER_NAME, theCastVote);
    addSecondDummies();

    voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

    assertTrue(vote.votesCast.get(7).proxyId.equals(TEST_USER_NAME));
  }

  @Test
  public void
      cast_vote_records_the_vote_with_same_user_votesCast_when_the_list_does_not_contain_same_user() {
    vote.parameters.canUpdate = true;

    addCastVote("OtherUser1", theCastVote);
    addCastVote("OtherUser2", theCastVote);
    addCastVote("OtherUser3", theCastVote);
    addCastVote("OtherUser4", theCastVote);
    voteManager.castVote(adminInfo.voteId, ballot, theCastVote);

    assertTrue(vote.votesCast.get(4).proxyId.equals(TEST_USER_NAME));
  }
}
