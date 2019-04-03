package org.rulez.demokracia.pdengine.dataobjects;

import java.util.List;

import javax.persistence.ElementCollection;

import org.rulez.demokracia.pdengine.RankedChoice;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;

public class CastVoteEntity extends BaseEntity {
	private static final long serialVersionUID = 1L;
	@ElementCollection
	public List<RankedChoice> preferences;
	public String proxyId;
	public String secretId;
	public List<String> assurances;

}
