package org.rulez.demokracia.pdengine.vote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class VoteControllerTest {

  @InjectMocks
  private VoteController voteController;

  @Mock
  private VoteService voteService;

  @Test
  public void create_vote_returns_ok() throws Exception {
    when(voteService.createVote(any())).thenReturn(new VoteAdminInfo("id", "admin"));
    ResponseEntity<VoteAdminInfo> responseEntity =
        voteController.createVote(new CreateVoteRequest());
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

}
