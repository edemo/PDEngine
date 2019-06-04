package org.rulez.demokracia.pdengine;

import org.rulez.demokracia.pdengine.choice.Choice;

public interface HasChoices extends VoteInterface {

  void addChoice(final Choice choice);

  Choice getChoice(final String choiceId);
}
