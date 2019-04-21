package org.rulez.demokracia.pdengine.voteCalculator;

import java.util.List;
import java.util.Map;

import org.rulez.demokracia.pdengine.beattable.Pair;

public class VoteResultTestHelper {

	public static List<VoteResult> createVoteResults() {
		return List.of(new VoteResult(List.of("A", "B"), Map.of()),
				new VoteResult(List.of("C"), Map.of("C", Map.of("A", new Pair(3, 1), "B", new Pair(3, 1)))),
				new VoteResult(List.of("D"),
						Map.of("D", Map.of("A", new Pair(4, 0), "B", new Pair(4, 0), "C", new Pair(3, 1)))));
	}

}
