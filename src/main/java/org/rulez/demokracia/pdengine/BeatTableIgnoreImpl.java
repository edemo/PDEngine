package org.rulez.demokracia.pdengine;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.rulez.demokracia.pdengine.dataobjects.Pair;

public class BeatTableIgnoreImpl implements BeatTableIgnore {

	@Override
	public BeatTable ignoreChoices(final BeatTable beatPathTable, final Collection<String> ignoredChoices) {
		Set<String> activeChoices = new HashSet<>(beatPathTable.getKeyCollection());
		activeChoices.removeAll(ignoredChoices);

		BeatTable filteredBeatTable = new BeatTable(activeChoices);
		for (String key1 : activeChoices) {
			for (String key2 : activeChoices) {
				Pair source = beatPathTable.getElement(key1, key2);
				filteredBeatTable.setElement(key1, key2, source);
			}
		}
		return filteredBeatTable;
	}
}
