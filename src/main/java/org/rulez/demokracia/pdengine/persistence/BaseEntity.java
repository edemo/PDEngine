package org.rulez.demokracia.pdengine.persistence;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseEntity implements IEntity {

	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	
	@Column(name = "Deleted")
	private Boolean _deleted;
	
	private Boolean _dirty;
	
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public BaseEntity() {
    	
    	propertyChangeSupport.addPropertyChangeListener(this);
    }
	
	public String getId() {
		return id;
	}

	public Boolean getDeleted() {
		return _deleted;
	}

	public void setDeleted(Boolean deleted) {
		if(!_deleted.equals(deleted)) {
			propertyChangeSupport.firePropertyChange("Deleted", deleted, _deleted);
			_deleted = deleted;
		}
	}

	public Boolean getDirty() {
		return _dirty;
	}

	public void propertyChange(PropertyChangeEvent event) {
		_dirty = true;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if(listener == null) throw new NullPointerException("listener");
		
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if(listener == null) throw new NullPointerException("listener");

		propertyChangeSupport.removePropertyChangeListener(listener);
	}
}
