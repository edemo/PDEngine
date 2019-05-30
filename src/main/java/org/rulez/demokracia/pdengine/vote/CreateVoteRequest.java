package org.rulez.demokracia.pdengine.vote;

import java.io.Serializable;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVoteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String voteName;
  private Set<String> neededAssurances;
  private Set<String> countedAssurances;
  private boolean isPrivate;
  private int minEndorsements;
}
