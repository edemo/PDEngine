package org.rulez.demokracia.pdengine.dataobjects;

import java.util.List;

import javax.persistence.ElementCollection;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;

public class ChoiceEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public String name;
	public String userName;
	@ElementCollection
	public List<String> endorsers;

}