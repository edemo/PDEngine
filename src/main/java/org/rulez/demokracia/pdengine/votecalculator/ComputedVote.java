package org.rulez.demokracia.pdengine.votecalculator;

import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;
import org.rulez.demokracia.pdengine.tally.Tally;
import org.rulez.demokracia.pdengine.vote.Vote;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ComputedVote extends BaseEntity {

  private static final long serialVersionUID = 1L;
  private BeatTable beatTable;
  private final Vote vote;
  private BeatTable beatPathTable;
  @ElementCollection
  private List<VoteResult> voteResults;
  @ElementCollection
  private Map<String, Tally> tallying;

  public ComputedVote(final Vote vote) {
    super();
    this.vote = vote;
  }
}
