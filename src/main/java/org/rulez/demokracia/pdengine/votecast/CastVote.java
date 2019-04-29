package org.rulez.demokracia.pdengine.votecast;

import java.util.ArrayList;
import java.util.List;

import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.choice.RankedChoice;

public class CastVote extends CastVoteEntity implements CastVoteInterface {

  private static final long serialVersionUID = 1L;

  public CastVote(final String proxyId, final List<RankedChoice> preferences) {
    super();
    setProxyId(proxyId);
    setPreferences(new ArrayList<>(preferences));
    setSecretId(RandomUtils.createRandomKey());
  }

  public CastVote(
      final String proxyId, final List<RankedChoice> preferences,
      final List<String> assurances
  ) {
    this(proxyId, preferences);
    setAssurances(assurances);
  }
}
