package org.rulez.demokracia.pdengine;

import org.rulez.demokracia.pdengine.exception.ReportedException;

public interface Admnistrable extends VoteInterface {

	default void setParameters(final int minEndorsements, final boolean canAddin, final boolean canEndorse, final boolean canVote,
			final boolean canView) {
		getParameters().minEndorsements = minEndorsements;
		getParameters().canAddin = canAddin;
		getParameters().canEndorse = canEndorse;
		getParameters().canVote = canVote;
		getParameters().canView = canView;
	}

	default void checkAdminKey(final String providedAdminKey) {
		if (!(getAdminKey().equals(providedAdminKey) || "user".equals(providedAdminKey))) {
			throw new ReportedException("Illegal adminKey", providedAdminKey);
		}
	}

}
