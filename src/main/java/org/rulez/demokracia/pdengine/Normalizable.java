package org.rulez.demokracia.pdengine;
import org.rulez.demokracia.pdengine.dataobjects.Pair;

public interface Normalizable extends ContainingBeats {

	long serialVersionUID = 1L;

	default void normalize() {
		for (String key : getKeyCollection()) {
			setElement(key, key, new Pair(0, 0));
		}
	}

}
