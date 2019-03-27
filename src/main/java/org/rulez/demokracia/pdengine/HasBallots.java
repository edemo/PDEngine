package org.rulez.demokracia.pdengine;

public interface HasBallots extends VoteInterface {

	default Integer getRecordedBallotsCount(final String userName) {
		return getRecordedBallots().containsKey(userName) ? getRecordedBallots().get(userName) : 0;
	}

	default void increaseRecordedBallots(final String key) {
		getRecordedBallots().put(key, getRecordedBallotsCount(key) + 1);
	}

	default boolean hasIssuedBallots() {
		return !getBallots().isEmpty();
	}

	default void addBallot(String ballot) {
		getBallots().add(ballot);
	}

	default void removeBallot(String ballot) {
		this.getBallots().remove(ballot);
	}

}
