package org.rulez.demokracia.pdengine.beattable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapMatrix<KeyType extends Serializable, ValueType extends Serializable> implements Matrix<KeyType, ValueType> {

	private static final long serialVersionUID = 1L;
	private final Map<KeyType,ConcurrentHashMap<KeyType,ValueType>> value = 
			new ConcurrentHashMap<>();
	private final Collection<KeyType> keyCollection;

	public MapMatrix(final Collection<KeyType> keyCollection) {
		for (KeyType key : keyCollection) {
			value.put(key, (ConcurrentHashMap<KeyType, ValueType>) newRow());
		}
		this.keyCollection=Collections.unmodifiableSet(
				new HashSet<KeyType>(keyCollection));
	}

	private Map<KeyType, ValueType> newRow() {
		return new ConcurrentHashMap<>();
	}

	@Override
	public void setElement(final KeyType columnKey, final KeyType rowKey, final ValueType value) {
		checkKeys(columnKey, rowKey);
		ConcurrentHashMap<KeyType, ValueType> column = this.value.get(columnKey);
		column.put(rowKey, value);
	}

	@Override
	public ValueType getElement(final KeyType columnKey, final KeyType rowKey) {
		checkKeys(columnKey, rowKey);
		ConcurrentHashMap<KeyType, ValueType> column = this.value.get(columnKey);
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
