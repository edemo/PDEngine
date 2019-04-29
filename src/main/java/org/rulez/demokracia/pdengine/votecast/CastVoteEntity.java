package org.rulez.demokracia.pdengine.votecast;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CastVoteEntity extends BaseEntity {

  private static final long serialVersionUID = 1L;
  @ElementCollection
  private List<RankedChoice> preferences;
  private String proxyId;
  private String secretId;
  private String signature;
  private List<String> assurances;

}
