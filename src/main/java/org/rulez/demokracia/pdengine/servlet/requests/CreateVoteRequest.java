package org.rulez.demokracia.pdengine.servlet.requests;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CreateVoteRequest {
	private String voteName;
	private Set<String> neededAssurances;
	private Set<String> countedAssurances;
	private boolean isPrivate;
	private int minEndorsements;
	
	public String getVoteName() {
		return voteName;
	}

	@XmlElement
	public void setVoteName(String voteName) {
		this.voteName = voteName;
	}
	public Set<String> getNeededAssurances() {
		return neededAssurances;
	}

	@XmlElement
	public void setNeededAssurances(Set<String> neededAssurances) {
		this.neededAssurances = neededAssurances;
	}
	public Set<String> getCountedAssurances() {
		return countedAssurances;
	}

	@XmlElement
	public void setCountedAssurances(Set<String> countedAssurances) {
		this.countedAssurances = countedAssurances;
	}
	public boolean isPrivate() {
		return isPrivate;
	}

	@XmlElement
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public int getMinEndorsements() {
		return minEndorsements;
	}

	@XmlElement
	public void setMinEndorsements(int minEndorsements) {
		this.minEndorsements = minEndorsements;
	}
}
