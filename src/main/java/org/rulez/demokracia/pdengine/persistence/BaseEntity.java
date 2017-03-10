package org.rulez.demokracia.pdengine.persistence;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.rulez.demokracia.pdengine.RandomUtils;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	
    public BaseEntity() {
    	id = RandomUtils.createRandomKey();
    }
	
}
