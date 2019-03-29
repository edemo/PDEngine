package org.rulez.demokracia.pdengine;

import java.util.Collection;

import jersey.repackaged.com.google.common.collect.Sets;

public class FloydWarshall {

	private TransitiveClosable beatTable;

	public FloydWarshall(final TransitiveClosable beatTable) {
		this.beatTable = beatTable;
	}

	public void computeTransitiveClosure() {
		Collection<Choice> keyCollection = beatTable.getKeyCollection();
		keyCollection.forEach(i -> keyCollection.forEach(j -> computeMinimalRoute(i, j)));
	}

	private void computeMinimalRoute(final Choice i, final Choice j) {
		if (!i.equals(j)) {
			beatTable.getKeyCollection().forEach(k -> selectShorterPath(i, j, k));
		}
	}

	private void selectShorterPath(final Choice i, final Choice j, final Choice k) {
		if (!Sets.newHashSet(i, j).contains(k)) {
			beatTable.selectShorterPath(i, j, k);
		}
	}
}
