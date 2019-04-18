package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.List;
import org.junit.Before;
import org.mockito.ArgumentMatchers;
import org.rulez.demokracia.pdengine.ComputedVote;
import org.rulez.demokracia.pdengine.VoteResult;
import org.rulez.demokracia.pdengine.VoteResultComposer;
import org.rulez.demokracia.pdengine.beattable.Pair;

public class CreatedComputedVote extends CreatedDefaultCastVoteWithRankedChoices {

	protected ComputedVote computedVote;
	protected List<VoteResult> result;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		getTheVote().setVotesCast(castVote);
		computedVote = new ComputedVote(getTheVote());
		computedVote.setVoteResultComposer(createVoteResultComposerMock());
		result = computedVote.computeVote();
	}

	private VoteResultComposer createVoteResultComposerMock() {
		VoteResultComposer voteResultComposerMock = mock(VoteResultComposer.class);

		when(voteResultComposerMock.composeResult(ArgumentMatchers.any())).thenReturn(List.of(
				new VoteResult(List.of("A", "B"), Map.of()),
				new VoteResult(List.of("C"), Map.of("C", Map.of("A", new Pair(3, 1), "B", new Pair(3, 1))))));
		return voteResultComposerMock;
	}

}
