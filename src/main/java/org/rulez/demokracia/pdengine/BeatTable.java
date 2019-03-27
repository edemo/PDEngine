package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.Collection;

import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.types.MapMatrix;
import org.rulez.demokracia.types.Matrix;

public class BeatTable extends MapMatrix<Choice, Pair> implements Matrix<Choice, Pair>, ContainingBeats{
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

	public Pair getPair(final Choice choice1, final Choice choice2) {
		return this.getElement(choice1, choice2);
	}

	public void setPair(final Choice choice1, final Choice choice2, final Pair pair) {
		checkPair(pair);

		this.setElement(choice1, choice2, pair);
	}
}
