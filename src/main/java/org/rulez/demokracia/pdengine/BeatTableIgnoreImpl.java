package org.rulez.demokracia.pdengine;

import java.util.Collection;
import org.rulez.demokracia.pdengine.dataobjects.Pair;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class BeatTableIgnoreImpl implements BeatTableIgnore {

	@Override
	public BeatTable ignoreChoices(final BeatTable beatPathTable, final Collection<String> ignoredChoices) {
		SetView<String> activeChoices = Sets.difference(Sets.newHashSet(beatPathTable.getKeyCollection()),
				Sets.newHashSet(ignoredChoices));
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
