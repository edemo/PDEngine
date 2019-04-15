package org.rulez.demokracia.pdengine;

import java.util.Collection;
import java.util.List;

public interface WinnerCalculator {

  List<String>
      calculateWinners(BeatTable beatTable, Collection<String> ignoredChoices);
}
