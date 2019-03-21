package org.rulez.demokracia.pdengine.dataobjects;

import java.util.ArrayList;
import java.util.List;

import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.RankedChoice;


public class CastVote extends CastVoteEntity{
	private static final long serialVersionUID = 1L;
	
	public CastVote(String proxyId, List<RankedChoice> preferences) {
		super();
		this.proxyId = proxyId;
		this.preferences = new ArrayList<>(preferences);
		this.secretId = RandomUtils.createRandomKey();
	}

	public List<RankedChoice> getPreferences(){
		return this.preferences;
	}
}
