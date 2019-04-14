package org.rulez.demokracia.pdengine;

import java.util.Iterator;
import java.util.List;

public interface Voteable extends VoteInterface {

	default CastVote addCastVote(final String proxyId, final List<RankedChoice> theVote) {
		CastVote castVote = new CastVote(proxyId, theVote);
		getVotesCast().add(castVote);
		return castVote;
	}
	
	default CastVote addCastVote(final String proxyId, final List<RankedChoice> theVote, final List<String> assurances) {
		Iterator<CastVote> listIterator = getVotesCast().iterator();
		while (listIterator.hasNext()) {
			CastVote element = listIterator.next();

			if (element.proxyId.equals(proxyId)) {
				listIterator.remove();
			}
		}

		CastVote castVote = new CastVote(proxyId, theVote, assurances);
		getVotesCast().add(castVote);
		castVote.updateSignature();
		return castVote;
	}

}
