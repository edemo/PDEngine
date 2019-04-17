package org.rulez.demokracia.pdengine;

import java.util.List;

import org.rulez.demokracia.pdengine.choice.RankedChoice;

public interface CastVoteInterface {

	List<RankedChoice> getPreferences();

	List<String> getAssurances();
}
