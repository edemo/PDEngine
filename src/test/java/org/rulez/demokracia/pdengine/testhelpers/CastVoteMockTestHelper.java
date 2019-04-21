package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.rulez.demokracia.pdengine.votecast.CastVote;

public class CastVoteMockTestHelper {

	public static List<CastVote> createMockedCastVotes() {
		return castVoteMocksWithIncrementalAssurances(5);
	}

	private static List<CastVote> castVoteMocksWithIncrementalAssurances(final int limit) {
		return IntStream.range(1, limit)
				.boxed()
				.map(CastVoteMockTestHelper::nFirstIntAsString)
				.map(CastVoteMockTestHelper::castVoteMockFromAssurances)
				.collect(Collectors.toList());
	}

	private static List<String> nFirstIntAsString(final Integer limit) {
		return IntStream.range(0, limit)
				.boxed()
				.map(Object::toString)
				.collect(Collectors.toList());
	}

	private static CastVote castVoteMockFromAssurances(final List<String> assuranceList) {
		CastVote castVote = mock(CastVote.class);
		when(castVote.getAssurances()).thenReturn(assuranceList);
		return castVote;
	}
}
