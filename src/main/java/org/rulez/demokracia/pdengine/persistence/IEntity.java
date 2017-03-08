package org.rulez.demokracia.pdengine.persistence;

import java.beans.*;
import java.io.Serializable;

public interface IEntity extends Serializable, PropertyChangeListener {

	String getId();
	Boolean getDeleted();
	void setDeleted(Boolean deleted);
	Boolean getDirty(); 
	
	void propertyChange(PropertyChangeEvent event);
	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
}
