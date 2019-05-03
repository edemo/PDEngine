package org.rulez.demokracia.pdengine.tally;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tally {

  private String assurance;

  public Tally(final String assurance) {
    this.assurance = assurance;
  }

}
