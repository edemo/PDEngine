package org.rulez.demokracia.pdengine.beattable;

import java.util.Collection;

public interface BeatTableIgnoreService {

  BeatTable
      ignoreChoices(BeatTable beatPathTable, Collection<String> ignoredChoices);

}
