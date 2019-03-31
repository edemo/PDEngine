package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.rulez.demokracia.pdengine.dataobjects.Pair;
import org.rulez.demokracia.types.MapMatrix;

public class BeatTable extends MapMatrix<String, Pair> implements TransitiveClosable, Normalizable {
	private static final long serialVersionUID = 1L;

	public BeatTable(final BeatTable beatTable) {
		super(beatTable.getKeyCollection());
		for (String row : getKeyCollection()) {
			for (String col : getKeyCollection()) {
				Pair sourcePair = beatTable.getElement(col, row);
				if (Objects.nonNull(sourcePair)) {
					this.setElement(col, row, new Pair(sourcePair.winning, sourcePair.losing));
				}
			}
		}
	}

	public enum Direction {
		DIRECTION_FORWARD, DIRECTION_BACKWARD
	}

	public BeatTable() {
		this(new ArrayList<String>());
	}

	public BeatTable(final Collection<String> keyCollection) {
		super(keyCollection);
	}
}
