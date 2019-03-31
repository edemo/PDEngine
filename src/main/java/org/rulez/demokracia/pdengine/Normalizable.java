package org.rulez.demokracia.pdengine;

import java.util.Collection;

import org.rulez.demokracia.pdengine.BeatTable.Direction;
import org.rulez.demokracia.pdengine.dataobjects.Pair;

public interface Normalizable extends ContainingBeats {

	long serialVersionUID = 1L;

	default void normalize() {
		for (String key : getKeyCollection()) {
			setElement(key, key, new Pair(0, 0));
		}

		Collection<String> keys = getKeyCollection();

		for (String key1 : keys) {
			for (String key2 : keys) {
				int key1Win = beatInformation(key1, key2, Direction.DIRECTION_FORWARD);
				int key2Win = beatInformation(key2, key1, Direction.DIRECTION_FORWARD);

				if (key1Win > key2Win)
					setElement(key2, key1, new Pair(0, 0));
				if (key1Win == key2Win) {
					int key1Los = beatInformation(key1, key2, Direction.DIRECTION_BACKWARD);
					int key2Los = beatInformation(key2, key1, Direction.DIRECTION_BACKWARD);

					if (key1Los == key2Los) {
						setElement(key2, key1, new Pair(0, 0));
						setElement(key1, key2, new Pair(0, 0));
					}
				}
			}
		}
	}

}
