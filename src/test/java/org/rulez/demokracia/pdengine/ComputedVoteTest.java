package org.rulez.demokracia.pdengine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.pdengine.testhelpers.CreatedComputedVote;

@TestedFeature("Vote")
@TestedOperation("Compute vote results")
public class ComputedVoteTest extends CreatedComputedVote {

	private static final Pair ZERO_PAIR = new Pair(0, 0);
	private BeatTable beatTable;

	@Before
	@Override
	public void setUp() {
		super.setUp();
		beatTable = computedVote.getBeatTable();
	}

	@TestedBehaviour("compares and stores initial beat matrix")
	@Test
	public void compute_vote_should_create_initial_matrix_with_full_key_set() {
		assertEquals(Set.of("A", "B", "C", "D"), Set.copyOf(beatTable.getKeyCollection()));
	}

	@TestedBehaviour("compares and stores initial beat matrix")
	@Test
	public void compute_vote_should_create_empty_initial_matrix_when_voteCast_is_empty() {
		getTheVote().setVotesCast(new ArrayList<>());
		ComputedVote tmpVote = new ComputedVote(getTheVote());
		tmpVote.computeVote();
		beatTable = tmpVote.getBeatTable();
		assertEquals(Set.of(), Set.copyOf(beatTable.getKeyCollection()));
	}

	@TestedBehaviour("compares and stores initial beat matrix")
	@Test
	public void after_compute_vote_beat_table_should_contain_beat_information() {
		Pair abPair = beatTable.getElement("A", "B");
		assertPairInitialized(abPair);
	}

	@TestedBehaviour("calculates and stores beatpath matrix")
	@Test
	public void beat_path_matrix_is_not_the_same_as_initial_matrix() {
		assertNotSame(computedVote.getBeatTable(), computedVote.getBeatPathTable());
	}

	@TestedBehaviour("calculates and stores beatpath matrix")
	@Test
	public void beat_path_matrix_is_normalized() {
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
		BeatTable firstBeatTable = new BeatTable(computedVote.getBeatPathTable());

		computedVote.getBeatPathTable().computeTransitiveClosure();
		BeatTable secondBeatTable = computedVote.getBeatPathTable();

		assertBeatTableEquals(firstBeatTable, secondBeatTable);
	}

	@TestedBehaviour("the winners list contains the looses to the first one")
	@Test
	public void computedVote_returns_the_loses_against_first_ones() {
		List<VoteResult> computeVote = computedVote.computeVote();
		Set<String> expectedKeySet = Set.of("A", "B");
		Set<String> actualKeySet = computeVote.get(1).getBeats().get("C").keySet();
		assertEquals(expectedKeySet, actualKeySet);
	}

	private void assertBeatTableEquals(final BeatTable firstBeatTable, final BeatTable secondBeatTable) {
		for (String choice1 : firstBeatTable.getKeyCollection()) {
			for (String choice2 : firstBeatTable.getKeyCollection()) {
				assertEquals(secondBeatTable.getElement(choice1, choice2), firstBeatTable.getElement(choice1, choice2));
			}
		}
	}

	private void assertPairInitialized(final Pair pair) {
		assertNotNull(pair);
		assertNotEquals(0, pair.winning + pair.losing);
	}

	@TestedBehaviour("vote result includes the votes cast with the secret cast vote identifier.")
	@Test
	public void vote_result_includes_the_votes_cast_with_the_secret_cast_vote_id() {
		String secretId = computedVote.getVote().getVotesCast().get(0).getSecretId();
		assertFalse(secretId.isEmpty());
	}
}
