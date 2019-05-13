package org.rulez.demokracia.pdengine.beattable;

import java.util.List;
import org.rulez.demokracia.pdengine.votecast.CastVote;

public interface BeatTableService {

  BeatTable initializeBeatTable(List<CastVote> castVotes);

  BeatTable normalize(BeatTable original);

}
