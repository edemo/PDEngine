package org.rulez.demokracia.PDEngine.integrationtest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.PDEngine.IVoteManager;
import org.rulez.demokracia.PDEngine.DataObjects.Vote;
import org.rulez.demokracia.PDEngine.DataObjects.VoteAdminInfo;
import org.rulez.demokracia.PDEngine.integrationtest.JettyThread;
import org.rulez.demokracia.PDEngine.servlet.requests.CreateVoteRequest;

public class VoteCreationIntegrationTest {
	private JettyThread thread;
	private CreateVoteRequest req;

	@Before
    public void setUp(){
		thread = new JettyThread();
        thread.run();
		initializeCreateVoteRequest();
    }
	
	@Test
	public void vote_can_be_created_through_rest_interface() {
		Invocation.Builder invocationBuilder = createWebClient();
		Response response = invocationBuilder.post(Entity.entity(req,MediaType.APPLICATION_JSON));
		VoteAdminInfo adminInfo = response.readEntity(VoteAdminInfo.class);
		Vote vote = IVoteManager.getVoteManager().getVote(adminInfo.getVoteId());
		assertEquals(req.getVoteName(),vote.name);
		assertEquals(adminInfo.getAdminKey(),vote.adminKey);
	}

	private Invocation.Builder createWebClient() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://127.0.0.1:8080/vote");
		Invocation.Builder invocationBuilder =
				webTarget.request(MediaType.APPLICATION_JSON);
		return invocationBuilder;
	}

	private CreateVoteRequest initializeCreateVoteRequest() {
		req = new CreateVoteRequest();
		req.setVoteName("voteName");
		Set<String> countedAssurances = new HashSet<String>();
		countedAssurances.add("");
		countedAssurances.add("magyar");
		req.setCountedAssurances(countedAssurances);
		req.setMinEndorsements(3);
		Set<String> neededAssurances = new HashSet<String>();
		neededAssurances.add("magyar");
		req.setNeededAssurances(neededAssurances);
		req.setPrivate(false);
		return req;
	}

	@After
	public void tearDown() throws Exception {
		thread.end();
	}
}
