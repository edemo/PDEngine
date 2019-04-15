package org.rulez.demokracia.pdengine.servlet.requests;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateVoteRequest {

  @XmlElement
  public String voteName;
  @XmlElement
  public Set<String> neededAssurances;
  @XmlElement
  public Set<String> countedAssurances;
  @XmlElement
  public boolean isPrivate;
  @XmlElement
  public int minEndorsements;
}
