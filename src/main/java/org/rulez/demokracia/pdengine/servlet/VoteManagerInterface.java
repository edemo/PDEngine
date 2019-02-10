package org.rulez.demokracia.pdengine.servlet;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;

import org.json.JSONObject;
import org.rulez.demokracia.pdengine.IVoteManager;
import org.rulez.demokracia.pdengine.RandomUtils;
import org.rulez.demokracia.pdengine.dataobjects.VoteAdminInfo;
import org.rulez.demokracia.pdengine.exception.ReportedException;
import org.rulez.demokracia.pdengine.servlet.requests.CreateVoteRequest;

@Path("vote")
public class VoteManagerInterface {

    private static final Logger LOGGER = Logger.getLogger( RandomUtils.class.getName() );
    
    @Resource
    private WebServiceContext wsContext;

	@POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
	public Response createVote(CreateVoteRequest req) {
		VoteAdminInfo adminInfo;
		try {
			adminInfo = IVoteManager.getVoteManager(wsContext).createVote(
					req.getVoteName(),
					req.getNeededAssurances(),
					req.getCountedAssurances(),
					req.isPrivate(),
					req.getMinEndorsements());
		} catch (ReportedException e) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("error", e.toJSON());
			jsonObject.put("input", new JSONObject(req));
			String result = jsonObject.toString(1);
			LOGGER.log(Level.FINE, result);
			return Response.status(400).entity(result).build();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("adminKey", adminInfo.adminKey);
		jsonObject.put("voteId", adminInfo.voteId);
		String result = jsonObject.toString(1);
		return Response.status(200).entity(result).build();
	}
}
