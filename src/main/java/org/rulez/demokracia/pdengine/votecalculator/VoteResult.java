package org.rulez.demokracia.pdengine.votecalculator;

import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.persistence.BaseEntity;

import lombok.Getter;

@Getter
@Entity
public class VoteResult extends BaseEntity {

  private static final long serialVersionUID = 1L;

  @ElementCollection
  private final List<String> winners;
  @ElementCollection
  private final Map<String, VoteResultBeat> beats;

  public VoteResult(
      final List<String> winners, final Map<String, VoteResultBeat> beats
  ) {
    super();
    this.winners = winners;
    this.beats = beats;
  }

}
