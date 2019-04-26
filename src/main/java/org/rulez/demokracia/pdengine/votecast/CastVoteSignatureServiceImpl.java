package org.rulez.demokracia.pdengine.votecast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.rulez.demokracia.pdengine.choice.RankedChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CastVoteSignatureServiceImpl implements CastVoteSignatureService {

  private static final String DELIMITER = "|";

  @Autowired
  private MessageSignerService messageSigner;

  @Override
  public void signCastVote(final CastVote castVote) {
    castVote.setSignature(
        messageSigner
            .signatureOfMessage(contentToBeSigned(castVote))
    );

  }

  private String contentToBeSigned(final CastVote castVote) {
    List<String> params =
        new ArrayList<>(List.of(castVote.getProxyId(), castVote.getSecretId()));

    castVote.getPreferences()
        .forEach(rc -> appendListWithRankedChoice(params, rc));
    return params.stream().collect(Collectors.joining(DELIMITER));
  }

  private void appendListWithRankedChoice(
      final List<String> params, final RankedChoice rankedChoice
  ) {
    params.addAll(
        List.of(
            rankedChoice.getId(), rankedChoice.getChoiceId(),
            Integer.toString(rankedChoice.getRank())
        )
    );
  }

}
