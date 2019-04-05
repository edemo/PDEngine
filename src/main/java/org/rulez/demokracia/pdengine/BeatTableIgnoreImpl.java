package org.rulez.demokracia.pdengine;

import java.util.Collection;
import java.util.Objects;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

import jersey.repackaged.com.google.common.collect.Sets;
import jersey.repackaged.com.google.common.collect.Sets.SetView;

public class BeatTableIgnoreImpl implements BeatTableIgnore {

	@Override
	public BeatTable ignoreChoices(final BeatTable beatPathTable, final Collection<String> ignoredChoices) {
		SetView<String> activeChoices = Sets.difference(Sets.newHashSet(beatPathTable.getKeyCollection()),
				Sets.newHashSet(ignoredChoices));
		BeatTable filteredBeatTable = new BeatTable(activeChoices);
		for (String key1 : activeChoices) {
			for (String key2 : activeChoices) {
				Pair source = beatPathTable.getElement(key1, key2);
				if (Objects.nonNull(source))
					filteredBeatTable.setElement(key1, key2, source);
			}
		}
		return filteredBeatTable;
	}
}
