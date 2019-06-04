package org.rulez.demokracia.pdengine.tally;

import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Tally extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private String assurance;

  public Tally(final String assurance) {
    super();
    this.assurance = assurance;
  }

}
