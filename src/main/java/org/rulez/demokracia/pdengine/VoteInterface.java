package org.rulez.demokracia.pdengine;

import java.util.List;
import java.util.Map;

import org.rulez.demokracia.pdengine.choice.Choice;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.votecast.CastVote;

public interface VoteInterface {

  VoteParameters getParameters();

  String getAdminKey();

  Map<String, Choice> getChoices();

  Map<String, Integer> getRecordedBallots();

  List<String> getBallots();

  List<CastVote> getVotesCast();

  String getId();

  List<String> getNeededAssurances();

}
