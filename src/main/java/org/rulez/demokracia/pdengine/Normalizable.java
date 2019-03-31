package org.rulez.demokracia.pdengine;

import java.util.Collection;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

public interface Normalizable extends ContainingBeats {

	long serialVersionUID = 1L;

	default void normalize() {
		Collection<String> keys = getKeyCollection();
		
		for (String key1 : keys) {
			for (String key2 : keys) {
				if(key1.contentEquals(key2))
					setElement(key1, key2, new Pair(0, 0));
				
				Pair beats1 = getPair(key1, key2);
				Pair beats2 = getPair(key2, key1);
				Pair pair = compareBeats(beats1, beats2);
				
				if(pair.equals(beats1))
					setElement(key2, key1, new Pair(0, 0));
				if(pair.equals(beats2))
					setElement(key1, key2, new Pair(0, 0));
			}
		}
	}
}
