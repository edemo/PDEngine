package org.rulez.demokracia.pdengine;

import java.util.Collection;

public interface BeatTableIgnore {

  BeatTable
      ignoreChoices(BeatTable beatPathTable, Collection<String> ignoredChoices);

}
