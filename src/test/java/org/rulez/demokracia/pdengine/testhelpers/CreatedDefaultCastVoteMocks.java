package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.rulez.demokracia.pdengine.CastVote;

public class CreatedDefaultCastVoteMocks extends CreatedDefaultChoice {

	protected List<CastVote> castVoteMocks;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		castVoteMocks = castVoteMocksWithIncrementalAssurances(5);
	}

	private List<CastVote> castVoteMocksWithIncrementalAssurances(final int limit) {
		return IntStream.range(1, limit)
				.boxed()
				.map(this::nFirstIntAsString)
				.map(this::castVoteMockFromAssurances)
				.collect(Collectors.toList());
	}

	private List<String> nFirstIntAsString(final Integer limit) {
		return IntStream.range(0, limit)
				.boxed()
				.map(Object::toString)
				.collect(Collectors.toList());
	}

	private CastVote castVoteMockFromAssurances(final List<String> assuranceList) {
		CastVote castVote = mock(CastVote.class);
		when(castVote.getAssurances()).thenReturn(assuranceList);
		return castVote;
	}
}
