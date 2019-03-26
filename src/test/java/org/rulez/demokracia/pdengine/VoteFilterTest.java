package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultCastVoteMocks;

public class VoteFilterTest extends CreatedDefaultCastVoteMocks {

	private VoteFilter voteFilter;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		voteFilter = new VoteFilter();
	}

	@TestedFeature("Supporting functionality")
	@TestedOperation("filter votes")
	@TestedBehaviour("null assurance means all of the votes")
	@Test
	public void filter_returns_full_list_on_null_assurance() {
		final List<CastVote> filteredVotes = voteFilter.filterVotes(castVoteMocks, null);
		assertEquals(castVoteMocks, filteredVotes);
	}

	@TestedFeature("Supporting functionality")
	@TestedOperation("filter votes")
	@TestedBehaviour("the output of the filter contains all votes with the given assurance")
	@Test
	public void filter_returns_all_votes_with_given_assurance() {
		final List<CastVote> expected = castVoteMocks.subList(1, 4);
		final List<CastVote> actual = voteFilter.filterVotes(castVoteMocks, "1");
		for (final CastVote vote : expected) {
			assertTrue(actual.contains(vote));
		}
	}

	@TestedFeature("Supporting functionality")
	@TestedOperation("filter votes")
	@TestedBehaviour("the output of the filter contains only votes with the given assurance")
	@Test
	public void filter_returns_only_votes_with_given_assurance() {
		final List<CastVote> actual = voteFilter.filterVotes(castVoteMocks, "3");
		for (final CastVote vote : actual) {
			assertTrue(vote.getAssurances().contains("3"));
		}
	}
}
