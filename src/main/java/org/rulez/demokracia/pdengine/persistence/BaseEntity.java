package org.rulez.demokracia.pdengine.persistence;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.rulez.demokracia.pdengine.RandomUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @SuppressWarnings("PMD.ShortVariable")
  private String id;

  public BaseEntity() {
    id = RandomUtils.createRandomKey();
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, "id");
  }

  @Override
  public boolean equals(final Object other) {
    return EqualsBuilder.reflectionEquals(this, other, "id");
  }
}
