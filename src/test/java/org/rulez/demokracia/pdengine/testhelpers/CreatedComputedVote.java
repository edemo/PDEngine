package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.List;
import org.junit.Before;
import org.mockito.ArgumentMatchers;
import org.rulez.demokracia.pdengine.ComputedVote;
import org.rulez.demokracia.pdengine.VoteResult;
import org.rulez.demokracia.pdengine.VoteResultComposer;
import org.rulez.demokracia.pdengine.dataobjects.Pair;

import com.google.common.collect.Sets;

public class CreatedComputedVote extends CreatedDefaultCastVoteWithRankedChoices {

	protected ComputedVote computedVote;
	protected List<VoteResult> result;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		getTheVote().votesCast = castVote;
		computedVote = new ComputedVote(getTheVote());
		computedVote.setVoteResultComposer(createVoteResultComposerMock());
		result = computedVote.computeVote();
	}

	private VoteResultComposer createVoteResultComposerMock() {
		VoteResultComposer voteResultComposerMock = mock(VoteResultComposer.class);
		Map<String, Map<String, Pair>> cMap = new HashMap<>();
		cMap.put("C",
				Sets.newHashSet("A", "B").stream().collect(Collectors.toMap(Function.identity(), a -> new Pair(3, 1))));

		when(voteResultComposerMock.composeResult(ArgumentMatchers.any())).thenReturn(Arrays.asList(
				new VoteResult(Arrays.asList("A", "B"), new HashMap<>()),
				new VoteResult(Arrays.asList("C"), cMap)));
		return voteResultComposerMock;
	}

}
