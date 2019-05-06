package org.rulez.demokracia.pdengine.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rulez.demokracia.pdengine.PDEngineMain;
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.vote.CreateVoteRequest;
import org.rulez.demokracia.pdengine.vote.Vote;
import org.rulez.demokracia.pdengine.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@TestedFeature("Manage votes")
@TestedOperation("create vote")
@TestedBehaviour("Creates a vote")
@RunWith(SpringRunner.class)
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = PDEngineMain.class
)
@AutoConfigureMockMvc
@TestPropertySource(
    locations = "classpath:application-integrationtest.yml"
)
@ActiveProfiles("integration-test")
public class VoteCreationIntegrationTest {

  private static final String ASSURANCE_NAME = "assurance";
  @Autowired
  private MockMvc mvc;

  @Autowired
  private VoteService voteService;
  private CreateVoteRequest voteRequest;
  private CreateVoteRequest badVoteRequest;

  @Before
  public void setUp() {
    voteRequest = initializeCreateVoteRequest("voteName");
    badVoteRequest =
        initializeCreateVoteRequest("`drop table little_bobby tables;`");
  }

  @Test
  public void vote_can_be_created_through_rest_interface() throws Exception {
    MvcResult result = callEndpointWithRequest(voteRequest)
        .andExpect(status().isOk())
        .andReturn();

    VoteAdminInfo info = new Gson().fromJson(
        result.getResponse().getContentAsString(), VoteAdminInfo.class
    );

    Vote savedVote = voteService.getVoteWithValidatedAdminKey(info);

    assertEquals(voteRequest.getVoteName(), savedVote.getName());
  }

  @Test
  public void vote_creation_fails_with_400_on_bad_input() throws Exception {
    callEndpointWithRequest(badVoteRequest).andExpect(status().isBadRequest());
  }

  @Test
  public void vote_creation_fails_and_reports_error_message_on_bad_input()
      throws Exception {
    callEndpointWithRequest(badVoteRequest)
        .andExpect(
            jsonPath(
                "error.message",
                CoreMatchers.equalTo("invalid characters in {0}")
            )
        );
  }

  @Test
  public void vote_creation_fails_and_reports_error_details_on_bad_input()
      throws Exception {
    callEndpointWithRequest(badVoteRequest)
        .andExpect(jsonPath("error.details[0]", CoreMatchers.equalTo("vote name")));
  }

  private CreateVoteRequest initializeCreateVoteRequest(final String name) {
    CreateVoteRequest req = new CreateVoteRequest();
    req.setVoteName(name);
    final Set<String> countedAssurances = new HashSet<>();
    countedAssurances.add("");
    countedAssurances.add(ASSURANCE_NAME);
    req.setCountedAssurances(countedAssurances);
    req.setMinEndorsements(3);
    Set<String> neededAssurances = new HashSet<>();
    neededAssurances.add(ASSURANCE_NAME);
    req.setNeededAssurances(neededAssurances);
    req.setPrivate(false);
    return req;
  }

  private ResultActions callEndpointWithRequest(
      final CreateVoteRequest voteRequest
  ) throws Exception {
    String req = new GsonBuilder().create().toJson(voteRequest);
    return mvc.perform(
        post("/vote")
            .accept(MediaType.APPLICATION_JSON)
            .content(req)
            .contentType(MediaType.APPLICATION_JSON)
    );
  }
}
