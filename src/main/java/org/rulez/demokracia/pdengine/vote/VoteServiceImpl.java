package org.rulez.demokracia.pdengine.vote;

import org.rulez.demokracia.pdengine.ValidationUtil;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteParameters;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {

  @Autowired
  private VoteRepository voteRepository;

  @Override
  public VoteAdminInfo createVote(final CreateVoteRequest createVoteRequest) {
    ValidationUtil.checkVoteName(createVoteRequest.getVoteName());

    VoteAdminInfo admininfo = new VoteAdminInfo();
    Vote vote = new Vote(createVoteRequest);
    admininfo.adminKey = vote.getAdminKey();
    admininfo.voteId = vote.getId();
    voteRepository.save(vote);
    return admininfo;
  }

  @Override
  public Vote getVote(final String voteId) {
    return voteRepository.findById(voteId)
        .orElseThrow(() -> new ReportedException("illegal voteId", voteId));
  }

  @Override
  public void
      modifyVote(final VoteAdminInfo voteAdminInfo, final String voteName) {
    ValidationUtil.checkVoteName(voteName);
    Vote vote = getModifiableVote(voteAdminInfo);

    vote.setName(voteName);
    saveVote(vote);
  }

  @Override
  public void deleteVote(final VoteAdminInfo adminInfo) {
    Vote vote = getModifiableVote(adminInfo);

    voteRepository.delete(vote);
  }

  private Vote getModifiableVote(final VoteAdminInfo adminInfo) {
    Vote vote = getVoteWithValidatedAdminKey(adminInfo);

    if (vote.hasIssuedBallots())
      throw new IllegalArgumentException(
          "This vote cannot be modified it has issued ballots."
      );
    return vote;
  }

  @Override
  public Vote getVoteWithValidatedAdminKey(final VoteAdminInfo adminInfo) {
    Vote vote = getVote(adminInfo.voteId);
    vote.checkAdminKey(adminInfo.adminKey);
    return vote;
  }

  @Override
  public void setVoteParameters(
      final VoteAdminInfo adminInfo, final VoteParameters voteParameters
  ) {
    checkMinEndorsementsIsNonnegative(voteParameters);

    Vote vote = getVoteWithValidatedAdminKey(adminInfo);

    vote.setParameters(
        voteParameters.getMinEndorsements(),
        voteParameters.isAddinable(),
        voteParameters.isEndorsable(),
        voteParameters.isVotable(),
        voteParameters.isViewable()
    );
  }

  private void
      checkMinEndorsementsIsNonnegative(final VoteParameters voteParameters) {
    if (voteParameters.getMinEndorsements() < 0)
      throw new ReportedException(
          "Illegal minEndorsements",
          Integer.toString(voteParameters.getMinEndorsements())
      );
  }

  @Override
  public Vote saveVote(final Vote vote) {
    return voteRepository.save(vote);
  }

}
