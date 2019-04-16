package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultChoice;

public class CastVoteTest extends CreatedDefaultChoice {

  @Override
  @Before
  public void setUp() {
    super.setUp();
  }

  @TestedFeature("Supporting functionality")
  @TestedOperation("CastVote")
  @TestedBehaviour("The preferences described by a cast vote can be obtained")
  @Test
  public void the_preferences_can_be_obtained_when_they_are_empty() {
    List<RankedChoice> theCastVote = new ArrayList<>();
    CastVote castVote = new CastVote(TEST_USER_NAME, theCastVote);
    List<RankedChoice> preferences = castVote.getPreferences();
    assertEquals(new ArrayList<>(), preferences);
  }

  @TestedFeature("Supporting functionality")
  @TestedOperation("CastVote")
  @TestedBehaviour("The preferences described by a cast vote can be obtained")
  @Test
  public void the_preferences_can_be_obtained_when_they_contain_choices() {
    List<RankedChoice> theCastVote = new ArrayList<>();
    theCastVote.add(new RankedChoice("1", 1));
    CastVote castVote = new CastVote(TEST_USER_NAME, theCastVote);
    List<RankedChoice> preferences = castVote.getPreferences();
    assertEquals(theCastVote, preferences);
  }

}
