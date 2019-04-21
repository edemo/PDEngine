package org.rulez.demokracia.pdengine.votefilter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.testhelpers.CastVoteMockTestHelper;
import org.rulez.demokracia.pdengine.votefilter.VoteFilterImpl;
import org.rulez.demokracia.pdengine.votecast.CastVote;

@TestedFeature("Supporting functionality")
@TestedOperation("filter votes")

@RunWith(MockitoJUnitRunner.class)
public class VoteFilterTest {

	@InjectMocks
	private VoteFilterImpl voteFilter;

	private List<CastVote> castVoteMocks;

	@Before
	public void setUp() {
		castVoteMocks = CastVoteMockTestHelper.createMockedCastVotes();
	}

	@TestedBehaviour("null assurance means all of the votes")
	@Test
	public void filter_returns_full_list_on_null_assurance() {
		final List<CastVote> filteredVotes = voteFilter.filterVotes(castVoteMocks, null);
		assertEquals(castVoteMocks, filteredVotes);
	}

	@TestedBehaviour("the output of the filter contains all votes with the given assurance")
	@Test
	public void filter_returns_all_votes_with_given_assurance() {
		final List<CastVote> expected = castVoteMocks.subList(1, 4);
		final List<CastVote> actual = voteFilter.filterVotes(castVoteMocks, "1");
		for (final CastVote vote : expected) {
			assertTrue(actual.contains(vote));
		}
	}

	@TestedBehaviour("the output of the filter contains only votes with the given assurance")
	@Test
	public void filter_returns_only_votes_with_given_assurance() {
		final List<CastVote> actual = voteFilter.filterVotes(castVoteMocks, "3");
		for (final CastVote vote : actual) {
			assertTrue(vote.getAssurances().contains("3"));
		}
	}
}
