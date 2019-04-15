package org.rulez.demokracia.types;

import java.io.Serializable;
import java.util.Collection;

public interface Matrix<KeyType extends Serializable,
    ValueType extends Serializable> extends Serializable {

  void setElement(KeyType columnKey, KeyType rowKey, ValueType value);

  ValueType getElement(KeyType columnKey, KeyType rowKey);

  Collection<KeyType> getKeyCollection();

}
