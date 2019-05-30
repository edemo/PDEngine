package org.rulez.demokracia.pdengine.user;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SuppressWarnings("PMD.ShortClassName")
public class User extends BaseEntity {

  private static final long serialVersionUID = 1L;
  private String proxyId;
  @ElementCollection
  private List<String> assurances;

  public User(final String proxyId) {
    super();
    this.proxyId = proxyId;
    assurances = new ArrayList<>();
  }
}
