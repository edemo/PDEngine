package org.rulez.demokracia.pdengine.dataobjects;

import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;

import org.rulez.demokracia.pdengine.CastVote;
import org.rulez.demokracia.pdengine.Choice;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;

public class VoteEntity extends BaseEntity {

  private static final long serialVersionUID = 1L;
  public String name;
  @ElementCollection
  public List<String> neededAssurances;
  @ElementCollection
  public List<String> countedAssurances;
  @ElementCollection
  public List<String> ballots;
  public boolean isPrivate;
  public String adminKey;
  public long creationTime;
  public VoteParameters parameters;
  @ElementCollection
  public Map<String, Choice> choices;
  @ElementCollection
  public List<CastVote> votesCast;
  public Map<String, Integer> recordedBallots;

}
