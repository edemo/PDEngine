package org.rulez.demokracia.pdengine;

import org.rulez.demokracia.pdengine.exception.ReportedException;

public interface HasChoices extends VoteInterface {

	default String addChoice(final String choiceName, final String user) {
		Choice choice = new Choice(choiceName, user);
		getChoices().put(choice.id, choice);
		return choice.id;
	}

	default Choice getChoice(final String choiceId) {
		if (!getChoices().containsKey(choiceId)) {
			throw new ReportedException("Illegal choiceId", choiceId);
		}
		return getChoices().get(choiceId);
	}
}
