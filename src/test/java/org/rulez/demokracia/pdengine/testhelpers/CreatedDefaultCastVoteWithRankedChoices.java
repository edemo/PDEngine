package org.rulez.demokracia.pdengine.testhelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.rulez.demokracia.pdengine.CastVote;
import org.rulez.demokracia.pdengine.RankedChoice;

public class CreatedDefaultCastVoteWithRankedChoices
    extends CreatedDefaultChoice {

  protected List<CastVote> castVote;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    castVote = createCastVote();
  }

  private List<CastVote> createCastVote() {
    List<CastVote> result = new ArrayList<>();
    result.add(createRankedChoices(Arrays.asList("A", "C", "B", "D")));
    result.add(createRankedChoices(Arrays.asList("D", "A", "B", "C")));
    result.add(createRankedChoices(Arrays.asList("B", "A", "C", "D")));
    result.add(createRankedChoices(Arrays.asList("B", "A", "C", "D")));
    for (int i = 0; i < 3; ++i) {
      result.add(createRankedChoices(Arrays.asList("A", "C")));
      result.add(createRankedChoices(Arrays.asList("C", "D")));
    }

    return result;
  }

  private CastVote createRankedChoices(final List<String> choices) {
    List<RankedChoice> rankedChoices = new ArrayList<>();
    for (int i = 0; i < choices.size(); ++i) {
      addRankedChoice(rankedChoices, choices.get(i), i);
    }
    return new CastVote("proxyId", rankedChoices);
  }

  private boolean addRankedChoice(
      final List<RankedChoice> rankedChoices, final String choice,
      final int rank
  ) {
    return rankedChoices.add(new RankedChoice(choice, rank));
  }

}
