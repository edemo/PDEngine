package org.rulez.demokracia.pdengine.dataobjects;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class VoteParameters implements Serializable {

  private static final long serialVersionUID = 1L;

  public int minEndorsements;
  public boolean canAddin;
  public boolean canEndorse;
  public boolean canVote;
  public boolean canView;
  public boolean canUpdate;
}
