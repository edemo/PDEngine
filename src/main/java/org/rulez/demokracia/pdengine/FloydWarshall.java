package org.rulez.demokracia.pdengine;

import java.util.Collection;

public class FloydWarshall {

	private final TransitiveClosable beatTable;

	public FloydWarshall(final TransitiveClosable beatTable) {
		this.beatTable = beatTable;
	}

	public void computeTransitiveClosure() {
		Collection<Choice> keyCollection = beatTable.getKeyCollection();
		keyCollection.forEach(i -> keyCollection.forEach(j -> computeMinimalRoute(i, j)));
	}

	private void computeMinimalRoute(final Choice choice1, final Choice choice2) {
		if (!choice1.equals(choice2)) {
			beatTable.getKeyCollection().forEach(k -> selectShorterPath(choice1, choice2, k));
		}
	}

	private void selectShorterPath(final Choice choice1, final Choice choice2, final Choice middleChoice) {
			beatTable.selectShorterPath(choice1, choice2, middleChoice);
	}
}
