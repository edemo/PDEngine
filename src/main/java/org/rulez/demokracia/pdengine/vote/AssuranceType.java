package org.rulez.demokracia.pdengine.vote;

public enum AssuranceType {
  NEEDED("needed"), COUNTED("counted");

  public final String description;

  AssuranceType(final String name) {
    description = name;
  }
}
