package org.rulez.demokracia.pdengine.dataobjects;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;

import org.rulez.demokracia.pdengine.RankedChoice;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;


public class CastVote extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	@ElementCollection
	public List<RankedChoice> preferences;
	public String proxyId;
	public String secretId;
	
	public CastVote() {
		super();
	}
	
	public CastVote(String proxyId, List<RankedChoice> preferences) {
		super();
		this.proxyId = proxyId;
		this.preferences = new ArrayList<RankedChoice>(preferences);
	}
	
	public CastVote(String proxyId, List<RankedChoice> preferences, String secretId) {
		this(proxyId, preferences);
		this.secretId = secretId;
	}

}
