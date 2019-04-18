package org.rulez.demokracia.pdengine.beattable;

import javax.persistence.Entity;

import org.rulez.demokracia.pdengine.persistence.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pair extends BaseEntity implements Comparable<Pair>{

	private static final long serialVersionUID = 1L;
	private int winning;
	private int losing;

	public Pair(final int winning, final int losing) {
		this.winning = winning;
		this.losing = losing;
	}

	@Override
	public int compareTo(final Pair other) {
		int winningDiff = this.winning - other.winning;
		return winningDiff == 0 ? other.losing - this.losing : winningDiff;
	}
}
