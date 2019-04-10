package org.rulez.demokracia.pdengine.testhelpers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.mockito.ArgumentMatchers;
import org.rulez.demokracia.pdengine.ComputedVote;
import org.rulez.demokracia.pdengine.VoteResult;
import org.rulez.demokracia.pdengine.VoteResultComposer;
import org.rulez.demokracia.pdengine.dataobjects.Pair;

public class CreatedComputedVote
    extends CreatedDefaultCastVoteWithRankedChoices {

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
    final VoteResultComposer voteResultComposerMock =
        mock(VoteResultComposer.class);
    when(voteResultComposerMock.composeResult(ArgumentMatchers.any()))
        .thenReturn(
            Arrays.asList(
                new VoteResult(Arrays.asList("A", "B"), new HashMap<>()),
                new VoteResult(Arrays.asList("C"),
                    Map.of(
                        "C",
                        Map.of("A", new Pair(3, 1), "B", new Pair(3, 1))
                    )
                )
            )
        );
    return voteResultComposerMock;
  }

}
