package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.Collection;

import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.types.MapMatrix;

public class BeatTable extends MapMatrix<Choice, Pair> implements ContainingBeats{
	private static final long serialVersionUID = 1L;

	public enum Direction {
		DIRECTION_FORWARD, DIRECTION_BACKWARD
	}

	public BeatTable() {
		this(new ArrayList<Choice>());
	}

	public BeatTable(final Collection<Choice> keyCollection) {
		super(keyCollection);
	}
}
