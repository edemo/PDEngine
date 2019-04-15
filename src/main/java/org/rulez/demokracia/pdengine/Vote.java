package org.rulez.demokracia.pdengine;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;

import com.google.gson.JsonObject;

@Entity
public class Vote extends VoteEntity implements VoteInterface, Admnistrable, //NOPMD
    HasChoices, HasBallots, Endorseable, Voteable {

  private static final long serialVersionUID = 1L;

  public Vote(
      final String voteName, final Collection<String> neededAssurances,
      final Collection<String> countedAssurances,
      final boolean isClosed, final int minEndorsements
  ) {
    super();
    name = voteName;
    adminKey = RandomUtils.createRandomKey();
    this.neededAssurances = new ArrayList<>(neededAssurances);
    this.countedAssurances = new ArrayList<>(countedAssurances);
    isPrivate = isClosed;
    parameters = new VoteParameters();
    parameters.minEndorsements = minEndorsements;
    creationTime = Instant.now().getEpochSecond();
    choices = new HashMap<>();
    ballots = new ArrayList<>();
    votesCast = new ArrayList<>();
    recordedBallots = new HashMap<>();
  }

  public JsonObject toJson() {
    return new VoteJSONSerializer().fromVote(this);
  }

  @Override
  public VoteParameters getParameters() {
    return parameters;
  }

  @Override
  public String getAdminKey() {
    return adminKey;
  }

  @Override
  public Map<String, Choice> getChoices() {
    return choices;
  }

  @Override
  public Map<String, Integer> getRecordedBallots() {
    return recordedBallots;
  }

  @Override
  public List<String> getBallots() {
    return ballots;
  }

  @Override
  public List<CastVote> getVotesCast() {
    return votesCast;
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public List<String> getNeededAssurances() {
    return new ArrayList<>(neededAssurances);
  }

  public List<CastVote> filterVotes(final String assurance) {
    return new VoteFilter().filterVotes(votesCast, assurance);
  }
}
