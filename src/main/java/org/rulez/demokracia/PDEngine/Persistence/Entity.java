package org.rulez.demokracia.PDEngine.Persistence;

import java.beans.*;
import java.io.Serializable;
import java.util.UUID;

public interface Entity extends Serializable, PropertyChangeListener {

	UUID getId();
	Boolean getDeleted();
	void setDeleted(Boolean deleted);
	Boolean getDirty(); 
	
	void propertyChange(PropertyChangeEvent event);
	void addPropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
}
