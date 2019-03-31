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
		secretId = RandomUtils.createRandomKey();
	}

	@Override
	public List<RankedChoice> getPreferences() {
		return preferences;
	}

	@Override
	public List<String> getAssurances() {
		throw new UnsupportedOperationException();
	}

	protected CastVote(CastVote cv) {
		proxyId  = cv.proxyId;
		secretId = cv.secretId;
		preferences = cv.preferences;
	}

	public String contentToBeSigned() {
		StringBuilder str  = new StringBuilder();
		final String DELIMITER="|";

		// TODO: decide what content should be signed, ids too?
		str.append(proxyId).append(DELIMITER);
		str.append(secretId).append(DELIMITER);

		for (RankedChoice rc : preferences) {
			str.append(rc.id).append(DELIMITER);
			str.append(rc.choiceId).append(DELIMITER);
			str.append(rc.rank).append(DELIMITER);
		}
		return str.toString();
	}

	public void updateSignature() {
		signature = MessageSigner.SignatureOfMessage(contentToBeSigned().getBytes());
	}
}
