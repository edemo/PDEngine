package org.rulez.demokracia.pdengine.dataobjects;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class VoteParameters implements Serializable{

	private static final long serialVersionUID = 1L;

	private int minEndorsements;
	private boolean addinable;
	private boolean endorsable;
	private boolean votable;
	private boolean viewable;
	private boolean updatable;
}