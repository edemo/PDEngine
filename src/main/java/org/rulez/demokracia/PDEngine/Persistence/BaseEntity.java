package org.rulez.demokracia.PDEngine.Persistence;

import java.beans.*;
import java.util.UUID;

import javax.persistence.*;
import org.hibernate.*;
import org.hibernate.annotations.*;

@DynamicUpdate(true)
public abstract class BaseEntity implements Entity {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "Id", unique = true)	
	private UUID _id;
	
	@Column(name = "Deleted")
	private Boolean _deleted;
	
	private Boolean _dirty;
	
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public BaseEntity() {
    	
    	propertyChangeSupport.addPropertyChangeListener(this);
    }
	
	public UUID getId() {
		return _id;
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
		_dirty = false;
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
