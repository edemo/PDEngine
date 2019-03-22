package org.rulez.demokracia.types;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class MapMatrix<KeyType extends Serializable, ValueType extends Serializable> implements Matrix<KeyType, ValueType> {

	private static final long serialVersionUID = 1L;
	private HashMap<KeyType,HashMap<KeyType,ValueType>> value = 
			new HashMap<KeyType,HashMap<KeyType,ValueType>>();
	private final Collection<KeyType> keyCollection;

	public MapMatrix(final Collection<KeyType> keyCollection) {
		for (KeyType key : keyCollection) {
			value.put(key, new HashMap<KeyType, ValueType>());
		}
		this.keyCollection=Collections.unmodifiableSet(
				new HashSet<KeyType>(keyCollection));
	}

	@Override
	public void setElement(KeyType columnKey, KeyType rowKey, ValueType value) {
		checkKeys(columnKey, rowKey);
		HashMap<KeyType, ValueType> column = this.value.get(columnKey);
		column.put(rowKey, value);
	}

	@Override
	public ValueType getElement(KeyType columnKey, KeyType rowKey) {
		checkKeys(columnKey, rowKey);
		HashMap<KeyType, ValueType> column = this.value.get(columnKey);
		return column.get(rowKey);
	}

	@Override
	public Collection<KeyType> getKeyCollection() {
		return keyCollection;
	}

	private void checkKeys(KeyType columnKey, KeyType rowKey) {
		checkKey(rowKey, "row");
		checkKey(columnKey, "column");
	}

	private void checkKey(KeyType key, String dimensionName) {
		if(!keyCollection.contains(key)) {
			throw new IllegalArgumentException("Invalid "+dimensionName+" key");
		}
	}

}
