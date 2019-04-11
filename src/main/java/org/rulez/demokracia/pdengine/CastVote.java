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

  public String contentToBeSigned() {
    final StringBuilder str = new StringBuilder();
    final String delimiter = "|";

    str.append(proxyId).append(delimiter)
        .append(secretId).append(delimiter);
    for (final RankedChoice rc : preferences)
      str.append(rc.id).append(delimiter)
          .append(rc.choiceId).append(delimiter)
          .append(rc.rank).append(delimiter);
    return str.toString();
  }

  public void updateSignature() {
    signature = MessageSigner.getInstance()
        .signatureOfMessage(contentToBeSigned().getBytes());
  }
}
