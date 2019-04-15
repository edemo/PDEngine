package org.rulez.demokracia.pdengine;

import java.util.List;

public interface CanCalculateWinners {

  List<String> calculateWinners(List<String> ignoredChoices);
}
