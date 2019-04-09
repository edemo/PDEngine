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
}
