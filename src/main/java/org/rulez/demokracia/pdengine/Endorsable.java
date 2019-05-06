package org.rulez.demokracia.pdengine;

public interface Endorsable extends VoteInterface {

	default boolean isEndorsable() {
		return getParameters().isEndorsable();
	}
}
