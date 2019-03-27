package org.rulez.demokracia.pdengine.integrationtest;

import static org.junit.Assert.assertEquals;

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
import org.rulez.demokracia.pdengine.annotations.TestedBehaviour;
import org.rulez.demokracia.pdengine.annotations.TestedFeature;
import org.rulez.demokracia.pdengine.annotations.TestedOperation;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.dataobjects.VoteEntity;
import org.rulez.demokracia.pdengine.servlet.requests.CreateVoteRequest;
import org.rulez.demokracia.pdengine.testhelpers.CreatedDefaultVoteRegistry;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class VoteCreationIntegrationTest extends CreatedDefaultVoteRegistry {
	private JettyThread thread;
	private CreateVoteRequest req;

	@Override
	@Before
	public void setUp(){
		thread = new JettyThread();
		thread.run();
		initializeCreateVoteRequest();
		super.setUp();
	}

	@TestedFeature("Manage votes")
	@TestedOperation("create vote")
	@TestedBehaviour("Creates a vote")
	@Test
	public void vote_can_be_created_through_rest_interface() {
		Invocation.Builder invocationBuilder = createWebClient();
		Response response = invocationBuilder.post(Entity.entity(req,MediaType.APPLICATION_JSON));
		VoteAdminInfo adminInfo = response.readEntity(VoteAdminInfo.class);
		VoteEntity vote = voteManager.getVote(adminInfo.voteId);
		assertVoteNameAndKeyIsCorrect(adminInfo, vote);
	}

	private void assertVoteNameAndKeyIsCorrect(final VoteAdminInfo adminInfo, final VoteEntity vote) {
		assertEquals(req.voteName,vote.name);
		assertEquals(adminInfo.adminKey,vote.adminKey);
	}

	@TestedFeature("Manage votes")
	@TestedOperation("create vote")
	@TestedBehaviour("Creates a vote")
	@Test
	public void vote_creation_fails_with_404_on_bad_input() {
		req.voteName= "`drop table little_bobby tables;`";
		Invocation.Builder invocationBuilder = createWebClient();
		Response response = invocationBuilder.post(Entity.entity(req,MediaType.APPLICATION_JSON));
		assertEquals(400, response.getStatus());
	}

	@TestedFeature("Manage votes")
	@TestedOperation("create vote")
	@TestedBehaviour("Creates a vote")
	@Test
	public void vote_creation_fails_and_reports_error_message_on_bad_input() {
		req.voteName= "`drop table little_bobby tables;`";
		Invocation.Builder invocationBuilder = createWebClient();
		Response response = invocationBuilder.post(Entity.entity(req,MediaType.APPLICATION_JSON));
		String responseString = response.readEntity(String.class);
		JsonObject responseJson = new JsonParser().parse(responseString).getAsJsonObject();
		assertEquals("invalid characters in {0}", responseJson
				.get("error").getAsJsonObject()
				.get("message").getAsString());
	}

	private Invocation.Builder createWebClient() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://127.0.0.1:8080/vote");
		return webTarget.request(MediaType.APPLICATION_JSON);
	}

	private CreateVoteRequest initializeCreateVoteRequest() {
		req = new CreateVoteRequest();
		req.voteName = "voteName";
		Set<String> countedAssurances = new HashSet<>();
		countedAssurances.add("");
		countedAssurances.add(ASSURANCE_NAME);
		req.countedAssurances = countedAssurances;
		req.minEndorsements=3;
		Set<String> neededAssurances = new HashSet<>();
		neededAssurances.add(ASSURANCE_NAME);
		req.neededAssurances=neededAssurances;
		req.isPrivate=false;
		return req;
	}

	@After
	public void tearDown() throws Exception {
		thread.end();
	}
}
