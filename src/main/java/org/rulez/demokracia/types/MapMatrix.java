package org.rulez.demokracia.types;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MapMatrix<KeyType extends Serializable, ValueType extends Serializable> implements Matrix<KeyType, ValueType> {

	private static final long serialVersionUID = 1L;
	private Map<KeyType,HashMap<KeyType,ValueType>> value = 
			new HashMap<>();
	private final Collection<KeyType> keyCollection;

	public MapMatrix(final Collection<KeyType> keyCollection) {
		for (KeyType key : keyCollection) {
			value.put(key, newRow());
		}
		this.keyCollection=Collections.unmodifiableSet(
				new HashSet<KeyType>(keyCollection));
	}

	private HashMap<KeyType, ValueType> newRow() {
		return new HashMap<KeyType, ValueType>();
	}

	@Override
	public void setElement(final KeyType columnKey, final KeyType rowKey, final ValueType value) {
		checkKeys(columnKey, rowKey);
		HashMap<KeyType, ValueType> column = this.value.get(columnKey);
		column.put(rowKey, value);
	}

	@Override
	public ValueType getElement(final KeyType columnKey, final KeyType rowKey) {
		checkKeys(columnKey, rowKey);
		HashMap<KeyType, ValueType> column = this.value.get(columnKey);
		return column.get(rowKey);
	}

	@Override
	public Collection<KeyType> getKeyCollection() {
		return keyCollection;
	}

	private void checkKeys(final KeyType columnKey, final KeyType rowKey) {
		checkKey(rowKey, "row");
		checkKey(columnKey, "column");
	}

	private void checkKey(final KeyType key, final String dimensionName) {
		if(!keyCollection.contains(key)) {
			throw new IllegalArgumentException("Invalid "+dimensionName+" key");
		}
	}

}
