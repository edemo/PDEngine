package org.rulez.demokracia.pdengine.choice;

import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.springframework.stereotype.Service;

@Service
public interface ChoiceModificationValidatorService {

  void validateModification(
      VoteAdminInfo voteAdminInfo, Vote vote, Choice choice
  );

  void validateDeletion(VoteAdminInfo voteAdminInfo, Vote vote, Choice choice);

}
