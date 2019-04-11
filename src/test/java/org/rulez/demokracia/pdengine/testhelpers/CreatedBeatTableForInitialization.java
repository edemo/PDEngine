package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.BeatTable;
import org.rulez.demokracia.pdengine.CastVote;
import org.rulez.demokracia.pdengine.RankedChoice;

public class CreatedBeatTableForInitialization extends CreatedBeatTable {

  public static final String FIRST = "first";
  public static final String SECOND = "second";
  public static final String THIRD = "third";
  public static final String FOURTH = "fourth";

  protected List<RankedChoice> preferences;

  protected RankedChoice rankedChoice1;
  protected RankedChoice rankedChoice2;
  protected RankedChoice rankedChoice3;
  protected RankedChoice rankedChoice4;

  protected List<CastVote> castVotes;

  protected CastVote castVote1;
  protected CastVote castVote2;
  protected CastVote castVote3;

  protected List<String> list;

  @Override
  @Before
  public void setUp() {
    super.setUp();

    preferences = new ArrayList<>();
    rankedChoice1 = new RankedChoice(FIRST, 2);
    rankedChoice2 = new RankedChoice(SECOND, 3);
    rankedChoice3 = new RankedChoice(THIRD, 1);

    preferences.add(rankedChoice1);
    preferences.add(rankedChoice2);
    preferences.add(rankedChoice3);

    castVotes = new ArrayList<>();
    castVote1 = new CastVote("TestUser1", preferences);
    castVote2 = new CastVote("TestUser2", preferences);
    castVote3 = new CastVote("TestUser3", new ArrayList<RankedChoice>());

    castVotes.add(castVote3);
    castVotes.add(castVote1);
    castVotes.add(castVote2);

    list = new ArrayList<>();
    list.add(FIRST);
    list.add(SECOND);
    list.add(THIRD);

    beatTable = new BeatTable(list);
  }

  public void createBeatTableWithSameRank() {
    rankedChoice4 = new RankedChoice(FOURTH, 1);
    preferences.add(rankedChoice4);
    castVotes.add(new CastVote("TestUser4", preferences));
    list.add(FOURTH);

    beatTable = new BeatTable(list);

  }

}
