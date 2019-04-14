package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.List;

import org.rulez.demokracia.pdengine.dataobjects.CastVoteEntity;

public class CastVote extends CastVoteEntity implements CastVoteInterface {
	private static final long serialVersionUID = 1L;

	public CastVote(final String proxyId, final List<RankedChoice> preferences) {
		super();
		this.proxyId = proxyId;
		this.preferences = new ArrayList<>(preferences);
		this.secretId = RandomUtils.createRandomKey();
	}
	
	public CastVote(final String proxyId, final List<RankedChoice> preferences, final List<String> assurances) {
		this(proxyId, preferences);
		this.assurances = assurances;
	}

	@Override
	public List<RankedChoice> getPreferences() {
		return preferences;
	}

	@Override
	public List<String> getAssurances() {
		return assurances;
	}

	public String contentToBeSigned() {
		StringBuilder str  = new StringBuilder();
		String delimiter="|";

		str.append(proxyId) .append(delimiter)
		   .append(secretId).append(delimiter);
		for (RankedChoice rc : preferences) {
			str.append(rc.id)      .append(delimiter)
			   .append(rc.choiceId).append(delimiter)
			   .append(rc.rank)    .append(delimiter);
		}
		return str.toString();
	}

	public void updateSignature() {
		signature = MessageSigner.getInstance().signatureOfMessage(contentToBeSigned().getBytes());
	}
}
