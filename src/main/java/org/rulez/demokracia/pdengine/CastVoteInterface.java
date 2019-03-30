package org.rulez.demokracia.pdengine;

import java.util.List;

public interface CastVoteInterface {

	List<RankedChoice> getPreferences();

	List<String> getAssurances();
}
