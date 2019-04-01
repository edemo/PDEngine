package org.rulez.demokracia.pdengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.util.ArrayList;
import java.util.List;

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
public class ComputedVoteTest extends CreatedDefaultCastVoteWithRankedChoices {

	private static final Pair ZERO_PAIR = new Pair(0, 0);

	@Before
	@Override
	public void setUp() {
		super.setUp();
		getTheVote().votesCast = castVote;
	}

	@TestedBehaviour("compares and stores initial beat matrix")
	@Test
	public void compute_vote_should_create_initial_matrix_with_full_key_set() {
		BeatTable beatTable = initBeatTable();
		assertEquals(Sets.newHashSet("A", "B", "C", "D"),
				Sets.newHashSet(beatTable.getKeyCollection()));
	}

	@TestedBehaviour("compares and stores initial beat matrix")
	@Test
	public void compute_vote_should_create_empty_initial_matrix_when_voteCast_is_empty() {
		getTheVote().votesCast = new ArrayList<>();
		BeatTable beatTable = initBeatTable();
		assertEquals(Sets.newHashSet(),
				Sets.newHashSet(beatTable.getKeyCollection()));
	}

	@TestedBehaviour("compares and stores initial beat matrix")
	@Test
	public void after_compute_vote_beat_table_should_contain_beat_information() {
		BeatTable beatTable = initBeatTable();
		Pair abPair = beatTable.getElement("A", "B");
		assertPairInitialized(abPair);
	}

	@TestedBehaviour("alculates and stores beatpath matrix")
	@Test
	public void beat_path_matrix_is_not_the_same_as_initial_matrix() {
		ComputedVote computedVote = new ComputedVote(getTheVote());
		computedVote.computeVote();
		assertNotSame(computedVote.getBeatTable(), computedVote.getBeatPathTable());
	}

	@TestedBehaviour("calculates and stores beatpath matrix")
	@Test
	public void beat_path_matrix_is_normalized() {
		ComputedVote computedVote = new ComputedVote(getTheVote());
		computedVote.computeVote();
		BeatTable beatPathTable = computedVote.getBeatPathTable();
		for (String choice1 : beatPathTable.getKeyCollection()) {
			for (String choice2 : beatPathTable.getKeyCollection()) {
				Pair beat1 = beatPathTable.getElement(choice1, choice2);
				Pair beat2 = beatPathTable.getElement(choice2, choice1);
				assertEquals(ZERO_PAIR, beatPathTable.lessBeat(beat1, beat2));
			}
		}
	}

	@TestedBehaviour("calculates and stores beatpath matrix")
	@Test
	public void transitive_closure_done_on_beat_path_matrix() {
		ComputedVote computedVote = new ComputedVote(getTheVote());

		computedVote.computeVote();
		BeatTable firstBeatTable = new BeatTable(computedVote.getBeatPathTable());

		computedVote.getBeatPathTable().computeTransitiveClosure();
		BeatTable secondBeatTable = computedVote.getBeatPathTable();

		assertBeatTableEquals(firstBeatTable, secondBeatTable);
	}

	private void assertBeatTableEquals(final BeatTable firstBeatTable, final BeatTable secondBeatTable) {
		for (String choice1 : firstBeatTable.getKeyCollection()) {
			for (String choice2 : firstBeatTable.getKeyCollection()) {
				assertEquals(secondBeatTable.getElement(choice1, choice2),
						firstBeatTable.getElement(choice1, choice2));
			}
		}
	}

	private void assertPairInitialized(final Pair pair) {
		assertNotNull(pair);
		assertNotEquals(0, pair.winning + pair.losing);
	}

	private BeatTable initBeatTable() {
		ComputedVote computedVote = new ComputedVote(getTheVote());
		computedVote.computeVote();
		return computedVote.getBeatTable();
	}
	
	@TestedBehaviour("vote result includes the votes cast with the secret cast vote identifier.")
	@Test
	public void vote_reulst_includes_the_votes_cast_with_the_secret_cast_vote_id() {
		ComputedVote computedVote = new ComputedVote(getTheVote());
		
		List<String> ids = computedVote.getSecretCastVoteIdentifiers();
		assertEquals(10, ids.size());
	}
}
