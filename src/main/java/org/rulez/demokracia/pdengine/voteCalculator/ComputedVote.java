package org.rulez.demokracia.pdengine.voteCalculator;

import java.util.List;
import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;
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
	private List<VoteResult> voteResults;


	public ComputedVote(final Vote vote) {
		this.vote = vote;
	}
}
