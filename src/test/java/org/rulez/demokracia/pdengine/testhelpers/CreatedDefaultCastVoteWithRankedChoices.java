package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.CastVote;
import org.rulez.demokracia.pdengine.RankedChoice;

public class CreatedDefaultCastVoteWithRankedChoices extends CreatedDefaultChoice {

	protected List<CastVote> castVote;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		castVote = createCastVote();
	}

	private List<CastVote> createCastVote() {
		List<CastVote> result = new ArrayList<>();
		result.add(createRankedChoices(Arrays.asList("A", "B", "C", "D")));
		result.add(createRankedChoices(Arrays.asList("D", "B", "A", "C")));
		result.add(createRankedChoices(Arrays.asList("B", "A", "C", "D")));

		return result;
	}

	private CastVote createRankedChoices(final List<String> choices) {
		List<RankedChoice> rankedChoices = new ArrayList<>();
		for (int i = 0; i < choices.size(); ++i) {
			rankedChoices.add(new RankedChoice(choices.get(i), i));
		}
		return new CastVote("proxyId", rankedChoices);
	}

}
