package org.rulez.demokracia.pdengine.voteCalculator;

import org.rulez.demokracia.pdengine.beattable.BeatTable;
import org.rulez.demokracia.pdengine.beattable.BeatTableService;
import org.rulez.demokracia.pdengine.beattable.BeatTableTransitiveClosureService;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputedVoteServiceImpl implements ComputedVoteService {

	@Autowired
	private VoteResultComposer voteResultComposer;

	@Autowired
	private BeatTableService beatTableService;

	@Autowired
	private BeatTableTransitiveClosureService beatTableTransitiveClosureService;

	@Override
	public ComputedVote computeVote(final Vote vote) {
		ComputedVote result = new ComputedVote(vote);

		result.setBeatTable(beatTableService.initializeBeatTable(vote.getVotesCast()));
		BeatTable beatPathTable = beatTableService.normalize(result.getBeatTable());
		result.setBeatPathTable(beatTableTransitiveClosureService.computeTransitiveClosure(beatPathTable));
		result.setVoteResults(voteResultComposer.composeResult(result.getBeatPathTable()));

		return result;
	}
}
