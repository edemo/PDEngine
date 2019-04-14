package org.rulez.demokracia.pdengine;

import java.util.ArrayList;
import java.util.List;

import org.rulez.demokracia.pdengine.dataobjects.CastVoteEntity;

public class CastVote extends CastVoteEntity implements CastVoteInterface {

  private static final long serialVersionUID = 1L;
  private static final String DELIMITER = "|";

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

  public String contentToBeSigned() {
    final StringBuilder str = new StringBuilder();

    str.append(proxyId).append(DELIMITER)
        .append(secretId).append(DELIMITER);
    for (final RankedChoice rc : preferences)
      str.append(rc.id).append(DELIMITER)
          .append(rc.choiceId).append(DELIMITER)
          .append(rc.rank).append(DELIMITER);
    return str.toString();
  }

  public void updateSignature() {
    signature = MessageSigner.getInstance()
        .signatureOfMessage(contentToBeSigned().getBytes());
  }
}
