package org.rulez.demokracia.pdengine.votecast;

import java.util.ArrayList;
import java.util.List;

import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.choice.RankedChoice;


public class CastVote extends CastVoteEntity implements CastVoteInterface {
	private static final long serialVersionUID = 1L;

	public CastVote(final String proxyId, final List<RankedChoice> preferences) {
		super();
		this.setProxyId(proxyId);
		this.setPreferences(new ArrayList<>(preferences));
		setSecretId(RandomUtils.createRandomKey());
	}

	@Override
	public List<String> getAssurances() {
		throw new UnsupportedOperationException();
	}
}
