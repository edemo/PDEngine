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
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
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

	@TestedFeature("Supporting functionality")
	@TestedOperation("filter votes")
	@TestedBehaviour("null assurance means all of the votes")
	@Test
	public void filter_returns_full_list_on_null_assurance() {
		List<CastVote> filteredVotes = VoteFilter.filterVotes(votes, null);
		assertEquals(votes, filteredVotes);
	}
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("filter votes")
	@TestedBehaviour("the output of the filter contains all votes with the given assurance")
	@Test
	public void filter_returns_all_votes_with_given_assurance() {
		List<CastVote> expected = votes.subList(1, 4);
		List<CastVote> actual = VoteFilter.filterVotes(votes, "1");
		assertEquals(expected, actual);
	}
	
	@TestedFeature("Supporting functionality")
	@TestedOperation("filter votes")
	@TestedBehaviour("the output of the filter contains only votes with the given assurance")
	@Test
	public void filter_returns_only_votes_with_given_assurance() {
		List<CastVote> actual = VoteFilter.filterVotes(votes, "3");
		for (CastVote vote : actual) {
			assertTrue(vote.getAssurances().contains("3"));
		}
	}
}
