package org.rulez.demokracia.pdengine.dataobjects;

import java.util.List;

import javax.persistence.ElementCollection;

import org.rulez.demokracia.pdengine.persistence.BaseEntity;

public class UserEntity extends BaseEntity {

  private static final long serialVersionUID = 1L;
  public String proxyId;
  @ElementCollection
  public List<String> assurances;
}
