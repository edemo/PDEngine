package org.rulez.demokracia.pdengine.integrationtest;

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

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rulez.demokracia.pdengine.annotations.tested_behaviour;
import org.rulez.demokracia.pdengine.annotations.tested_feature;
import org.rulez.demokracia.pdengine.annotations.tested_operation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.integrationtest.JettyThread;
import org.rulez.demokracia.pdengine.servlet.requests.CreateVoteRequest;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

public class VoteCreationIntegrationTest extends CreatedDefaultVoteRegistry {
	private JettyThread thread;
	private CreateVoteRequest req;

	@Before
    public void setUp() throws ReportedException{
		thread = new JettyThread();
        thread.run();
		initializeCreateVoteRequest();
		super.setUp();
    }
	
	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void vote_can_be_created_through_rest_interface() {
		Invocation.Builder invocationBuilder = createWebClient();
		Response response = invocationBuilder.post(Entity.entity(req,MediaType.APPLICATION_JSON));
		VoteAdminInfo adminInfo = response.readEntity(VoteAdminInfo.class);
		VoteEntity vote = voteManager.getVote(adminInfo.voteId);
		assertEquals(req.getVoteName(),vote.name);
		assertEquals(adminInfo.adminKey,vote.adminKey);
	}

	@tested_feature("Manage votes")
	@tested_operation("create vote")
	@tested_behaviour("Creates a vote")
	@Test
	public void vote_creation_fails_and_reports_error_with_bad_input() {
		req.setVoteName("`drop table little_bobby tables;`");
		Invocation.Builder invocationBuilder = createWebClient();
		Response response = invocationBuilder.post(Entity.entity(req,MediaType.APPLICATION_JSON));
		assertEquals(400, response.getStatus());
		String responseString = response.readEntity(String.class);
		JSONObject responseJson = new JSONObject(responseString);
		assertEquals("invalid characters in {0}", responseJson
				.getJSONObject("error")
				.getString("message"));
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
