package org.rulez.demokracia.pdengine.beattable;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class BeatTableIgnoreServiceImpl implements BeatTableIgnoreService {

  @Override
  public BeatTable ignoreChoices(
      final BeatTable beatTable, final Collection<String> ignoredChoices
  ) {
    Set<String> activeChoices =
        filterChoices(beatTable.getKeyCollection(), ignoredChoices);

    BeatTable filteredBeatTable = new BeatTable(activeChoices);
    activeChoices.forEach(
        key1 -> activeChoices
            .forEach(
                key2 -> copyElement(beatTable, filteredBeatTable, key1, key2)
            )
    );

    return filteredBeatTable;
  }

  private void copyElement(
      final BeatTable beatTable, final BeatTable filteredBeatTable,
      final String key1, final String key2
  ) {
    filteredBeatTable.setElement(key1, key2, beatTable.getElement(key1, key2));
  }

  private Set<String> filterChoices(
      final Collection<String> allChoices,
      final Collection<String> ignoredChoices
  ) {
    return allChoices.stream()
        .filter(choice -> !ignoredChoices.contains(choice))
        .collect(Collectors.toSet());
  }
}
