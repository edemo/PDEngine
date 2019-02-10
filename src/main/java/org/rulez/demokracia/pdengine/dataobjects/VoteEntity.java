package org.rulez.demokracia.pdengine.dataobjects;

import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;

import org.rulez.demokracia.pdengine.Choice;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;

public abstract class VoteEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public String name;
	@ElementCollection
	public List<String> neededAssurances;
	@ElementCollection
	public List<String> countedAssurances;
	@ElementCollection
	public List<String> ballots;
	public boolean isPrivate;
	public String adminKey;
	public long creationTime;
	public int minEndorsements;
	public boolean canAddin;
	public boolean canEndorse;
	public boolean canVote;
	public boolean canView;
	@ElementCollection
	public Map<String,Choice> choices;

	public VoteEntity() {
		super();
	}

}
