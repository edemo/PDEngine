package org.rulez.demokracia.pdengine;

import java.util.List;
import java.util.Map;

import org.rulez.demokracia.pdengine.dataobjects.Pair;

public class VoteResult {

	private final List<String> choices;
	private final Map<String, Map<String, Pair>> beats;

	public VoteResult(final List<String> choices, final Map<String, Map<String, Pair>> beats) {
		this.choices = choices;
		this.beats = beats;
	}

	public List<String> getChoices() {
		return choices;
	}

	public Map<String, Map<String, Pair>> getBeats() {
		return beats;
	}

}
