package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.BeatTable;
import org.rulez.demokracia.pdengine.RankedChoice;
import org.rulez.demokracia.pdengine.CastVote;

public class CreatedBeatTableForInitialization extends CreatedBeatTable{
	
	public final String first = "first";
	public final String second = "second";
	public final String third = "third";
	public final String fourth = "fourth";
	
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
	
	@Before
	public void setUp() {
		super.setUp();
		
		preferences = new ArrayList<RankedChoice>();
		rankedChoice1 = new RankedChoice(first, 2);
		rankedChoice2 = new RankedChoice(second, 3);
		rankedChoice3 = new RankedChoice(third, 1);
		
		preferences.add(rankedChoice1);
		preferences.add(rankedChoice2);
		preferences.add(rankedChoice3);
		
		castVotes = new ArrayList<CastVote>();
		castVote1 = new CastVote("TestUser1", preferences);
		castVote2 = new CastVote("TestUser2", preferences);
		castVote3 = new CastVote("TestUser3", new ArrayList<RankedChoice>());
		
		castVotes.add(castVote3);
		castVotes.add(castVote1);
		castVotes.add(castVote2);
		
		list = new ArrayList<String>();
		list.add(first);
		list.add(second);
		list.add(third);
		
		beatTable = new BeatTable(list);
	}
	
	public void createBeatTableWithInvalidRank() {
		rankedChoice4 = new RankedChoice(fourth, 1);
		preferences.add(rankedChoice4);
		castVotes.add(new CastVote("TestUser4", preferences));
		list.add(fourth);
		
		beatTable = new BeatTable(list);
		
		
	}
	
}
