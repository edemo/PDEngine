package org.rulez.demokracia.pdengine.vote;

import static org.rulez.demokracia.pdengine.vote.AssuranceType.COUNTED;
import static org.rulez.demokracia.pdengine.vote.AssuranceType.NEEDED;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.ValidationUtil;
import org.rulez.demokracia.pdengine.choice.Choice;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;
import org.rulez.demokracia.pdengine.votecast.CastVote;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VoteEntity extends BaseEntity {

  private static final long serialVersionUID = 1L;
  private String name;
  @ElementCollection
  private List<String> neededAssurances;
  @ElementCollection
  private List<String> countedAssurances;
  @ElementCollection
  private List<String> ballots;
  private boolean isPrivate;
  private String adminKey;
  private long creationTime;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "parameters_id", referencedColumnName = "id")
  private VoteParameters parameters;
  @ElementCollection
  private Map<String, Choice> choices;
  @ElementCollection
  private List<CastVote> votesCast;
  @ElementCollection
  private Map<String, Integer> recordedBallots;

  public VoteEntity(
      final String voteName, final Collection<String> neededAssurances,
      final Collection<String> countedAssurances,
      final boolean isPrivate, final int minEndorsements
  ) {
    super();
    name = voteName;
    adminKey = RandomUtils.createRandomKey();
    this.neededAssurances =
        ValidationUtil.checkAssurances(neededAssurances, NEEDED);
    this.countedAssurances =
        ValidationUtil.checkAssurances(countedAssurances, COUNTED);
    this.isPrivate = isPrivate;
    VoteParameters voteParameters = new VoteParameters();
    voteParameters.setMinEndorsements(minEndorsements);
    parameters = voteParameters;
    creationTime = Instant.now().getEpochSecond();
    choices = new HashMap<>();
    ballots = new ArrayList<>();
    votesCast = new ArrayList<>();
    recordedBallots = new HashMap<>();
  }

  public VoteEntity() {
    super();
  }

}
