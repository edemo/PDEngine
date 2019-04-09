package org.rulez.demokracia.pdengine;

import java.util.Iterator;
import java.util.List;

public interface Voteable extends VoteInterface {

  default CastVote
      addCastVote(final String proxyId, final List<RankedChoice> theVote) {
    Iterator<CastVote> listIterator = getVotesCast().iterator();
    while (listIterator.hasNext()) {
      CastVote element = listIterator.next();

      if (element.proxyId != null && element.proxyId.equals(proxyId)) {
        listIterator.remove();
      }
    }

    CastVote castVote = new CastVote(proxyId, theVote);
    getVotesCast().add(castVote);
    return castVote;
  }

}
