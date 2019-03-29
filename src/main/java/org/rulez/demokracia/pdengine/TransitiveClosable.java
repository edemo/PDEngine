package org.rulez.demokracia.pdengine;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

import jersey.repackaged.com.google.common.collect.Sets;

public interface TransitiveClosable extends ContainingBeats {

	default void computeTransitiveClosure() {
		FloydWarshall floydWarshall = new FloydWarshall(this);
		floydWarshall.computeTransitiveClosure();
	}

	default void selectShorterPath(final Choice i, final Choice j, final Choice k) {
		if (!Sets.newHashSet(i, j).contains(k)) {
			setElement(i, j,
					compareBeats(getElement(i, j),
							lessBeat(getElement(i, k), getElement(k, j))));
		}
	}

	default Pair lessBeat(final Pair a, final Pair b) {
		Pair greater = this.compareBeats(a, b);
		return greater.equals(a) ? b : a;
	}
}
