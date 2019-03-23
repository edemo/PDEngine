package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.CastVote;

public class VoteFilterTest {
	
	List<CastVote> votes;
	
	@Before
	public void setUp() {
		votes = new ArrayList<>();
		votes = IntStream.range(1, 5)
			.boxed()
			.map(this::nFirstIntAsString)
			.map(this::castVoteMockFromAssurances)
			.collect(Collectors.toList());
	}

	private List<String> nFirstIntAsString(Integer i) {
		return IntStream.range(0, i)
				.boxed()
				.map(Object::toString)
				.collect(Collectors.toList());
	}

	private CastVote castVoteMockFromAssurances(List<String> assuranceList) {
		CastVote castVote = mock(CastVote.class);
		when(castVote.getAssurances()).thenReturn(assuranceList);
		return castVote;
	}
	
	@tested_feature("Supporting functionality")
	@tested_operation("filter votes")
	@tested_behaviour("null assurance means all of the votes")
	@Test
	public void filter_returns_full_list_on_null_assurance() {
		List<CastVote> filteredVotes = VoteFilter.filterVotes(votes, null);
		assertEquals(votes, filteredVotes);
	}
}
