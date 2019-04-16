package org.rulez.demokracia.pdengine.dataobjects;

import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import org.rulez.demokracia.pdengine.CastVote;
import org.rulez.demokracia.pdengine.Choice;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VoteEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String name;
	@ElementCollection
	private List<String> neededAssurances;
	@ElementCollection
	private List<String> countedAssurances;
	@ElementCollection
	private List<String> ballots;
	private boolean isPrivate;
	private String adminKey;
	private long creationTime;
	private VoteParameters parameters;
	@ElementCollection
	private Map<String, Choice> choices;
	@ElementCollection
	private List<CastVote> votesCast;
	@ElementCollection
	private Map<String, Integer> recordedBallots;

}
