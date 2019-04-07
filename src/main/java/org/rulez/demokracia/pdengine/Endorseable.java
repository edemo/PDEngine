package org.rulez.demokracia.pdengine;

public interface Endorseable extends VoteInterface {

	default boolean isEndorseable() {
		return getParameters().canEndorse;
	}



}
