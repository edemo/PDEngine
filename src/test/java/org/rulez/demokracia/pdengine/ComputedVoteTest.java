package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultCastVoteWithRankedChoices;

import jersey.repackaged.com.google.common.collect.Sets;

@TestedFeature("Vote")
@TestedOperation("Compute vote results")
@TestedBehaviour("compares and stores initial beat matrix")
public class ComputedVoteTest extends CreatedDefaultCastVoteWithRankedChoices {

	@Before
	@Override
	public void setUp() {
		super.setUp();
		getTheVote().votesCast = castVote;
	}

	@Test
	public void compute_vote_should_create_initial_matrix_with_full_key_set() {
		BeatTable beatTable = initBeatTable();
		assertEquals(Sets.newHashSet("A", "B", "C", "D"),
				Sets.newHashSet(beatTable.getKeyCollection()));
	}

	@Test
	public void compute_vote_should_create_empty_initial_matrix_when_voteCast_is_empty() {
		getTheVote().votesCast = new ArrayList<>();
		BeatTable beatTable = initBeatTable();
		assertEquals(Sets.newHashSet(),
				Sets.newHashSet(beatTable.getKeyCollection()));
	}

	@Test
	public void after_compute_vote_beat_table_should_contain_beat_information() {
		BeatTable beatTable = initBeatTable();
		Pair abPair = beatTable.getElement("A", "B");
		assertPairInitialized(abPair);
	}

	private void assertPairInitialized(final Pair pair) {
		assertNotNull(pair);
		assertNotEquals(0, pair.winning + pair.losing);
	}

	private BeatTable initBeatTable() {
		ComputedVote computedVote = new ComputedVote(getTheVote());
		computedVote.computeVote();
		BeatTable beatTable = computedVote.getBeatTable();
		return beatTable;
	}
}
