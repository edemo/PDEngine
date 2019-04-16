package org.rulez.demokracia.pdengine;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ComputedVote implements ComputedVoteInterface, Serializable {

  private static final long serialVersionUID = 1L;
  private BeatTable beatTable;
  private final Vote vote;
  private BeatTable beatPathTable;
  private transient VoteResultComposer voteResultComposer;

  public ComputedVote(final Vote vote) {
    this.vote = vote;
    voteResultComposer = new VoteResultComposerImpl();
  }

  @Override
  public List<VoteResult> computeVote() {
    Set<String> keySet = computeKeySetFromVoteCast();

    initializeBeatTable(keySet);
    normalizeBeatTable();
    beatPathTable.computeTransitiveClosure();
    return voteResultComposer.composeResult(beatPathTable);
  }

  private void normalizeBeatTable() {
    beatPathTable = new BeatTable(beatTable);
    beatPathTable.normalize();
  }

  private void initializeBeatTable(final Set<String> keySet) {
    beatTable = new BeatTable(keySet);
    beatTable.initialize(vote.getVotesCast());
  }

  private Set<String> computeKeySetFromVoteCast() {
    return vote.getVotesCast().stream()
        .map(CastVote::getPreferences)
        .flatMap(List::stream)
        .map(p -> p.choiceId)
        .collect(Collectors.toSet());
  }

  public BeatTable getBeatTable() {
    return beatTable;
  }

  public BeatTable getBeatPathTable() {
    return beatPathTable;
  }

  public Vote getVote() {
    return vote;
  }

  public void
      setVoteResultComposer(final VoteResultComposer voteResultComposer) {
    this.voteResultComposer = voteResultComposer;
  }
}
