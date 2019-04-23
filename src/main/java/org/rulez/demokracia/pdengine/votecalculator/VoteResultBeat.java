package org.rulez.demokracia.pdengine.votecalculator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.beattable.Pair;
import org.rulez.demokracia.pdengine.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VoteResultBeat extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public VoteResultBeat() {
		this.beats = new ConcurrentHashMap<>();
	}

	public VoteResultBeat(final Map<String, Pair> beats) {
		this.beats = beats;
	}

	@ElementCollection
	private Map<String, Pair> beats;

}
