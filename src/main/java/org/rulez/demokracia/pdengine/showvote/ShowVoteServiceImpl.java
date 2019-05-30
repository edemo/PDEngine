package org.rulez.demokracia.pdengine.showvote;

import org.rulez.demokracia.pdengine.authentication.AuthenticatedUserService;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.vote.VoteEntity;
import org.rulez.demokracia.pdengine.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Service
public class ShowVoteServiceImpl implements ShowVoteService {

  @Autowired
  private VoteService voteService;

  @Autowired
  private AuthenticatedUserService authenticatedUserService;

  @Override
  public JsonObject showVote(final VoteAdminInfo adminInfo) {
    Vote vote = voteService.getVoteWithValidatedAdminKey(adminInfo);
    if (!adminInfo.adminKey.equals(vote.getAdminKey())) {
      checkAssurances(vote);
    }

    return voteToJSON(vote);
  }

  private final ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {

    @Override
    public boolean shouldSkipField(final FieldAttributes field) {
      return field.getDeclaringClass().equals(VoteEntity.class)
          && field.getName().equals("adminKey");
    }

    @Override
    public boolean shouldSkipClass(final Class<?> clazz) {
      return false;
    }
  };

  private JsonObject voteToJSON(final Vote vote) {
    Gson gsonBuilder =
        new GsonBuilder().addSerializationExclusionStrategy(exclusionStrategy).create();
    return gsonBuilder.toJsonTree(vote).getAsJsonObject();
  }

  private void checkAssurances(final Vote vote) {
    vote.getCountedAssurances().forEach(this::checkAnAssurance);
  }

  private void checkAnAssurance(final String assurance) {
    if (!authenticatedUserService.hasAssurance(assurance)) {
      throw new ReportedException("missing assurances", assurance);
    }
  }

}
