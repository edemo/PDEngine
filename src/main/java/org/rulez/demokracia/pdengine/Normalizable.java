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
				int key1Value = beatInformation(key1, key2, Direction.DIRECTION_FORWARD);
				int key2Value = beatInformation(key2, key1, Direction.DIRECTION_FORWARD);
				
				if(key1Value > key2Value)
					setElement(key2, key1, new Pair(0, 0));
			}
		}
	}

}
