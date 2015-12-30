package com.mhchoudh.appeal.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.mhchoudh.appeal.activities.CompleteappealActivity;
import com.mhchoudh.appeal.activities.NoSuchAppealException;
import com.mhchoudh.appeal.activities.AppealAlreadyCompletedException;
import com.mhchoudh.appeal.activities.AppealNotPaidException;
import com.mhchoudh.appeal.activities.ReadReplyActivity;
import com.mhchoudh.appeal.model.Identifier;
import com.mhchoudh.appeal.representations.AppealRepresentation;
import com.mhchoudh.appeal.representations.ReplyRepresentation;
import com.mhchoudh.appeal.representations.CSE564AppealsUri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/reply")
public class ReplyResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(ReplyResource.class);

    private @Context
    UriInfo uriInfo;

    public ReplyResource() {
        LOG.info("Reply Resource constructor");
    }

    /**
     * Used in test cases only to allow the injection of a mock UriInfo.
     * 
     * @param uriInfo
     */
    public ReplyResource(UriInfo uriInfo) {
        this.uriInfo = uriInfo;

    }

    @GET
    @Path("/{replyId}")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response getReply() {
        LOG.info("Retrieving a  Reply Resource");
        
        Response response;
        
        try {
            ReplyRepresentation responseRepresentation = new ReadReplyActivity().read(new CSE564AppealsUri(uriInfo.getRequestUri()));
            response = Response.ok().entity(responseRepresentation).build();
        } catch (AppealAlreadyCompletedException oce) {
            LOG.debug("Appeal already completed");
            response = Response.status(Status.NO_CONTENT).build();
        } catch (AppealNotPaidException onpe) {
            LOG.debug("Appeal not paid");
            response = Response.status(Status.NOT_FOUND).build();
        } catch (NoSuchAppealException nsoe) {
            LOG.debug("No such appeal");
            response = Response.status(Status.NOT_FOUND).build();
        }
        
        LOG.debug("The responce for the retrieve reply request is {}", response);
        
        return response;
    }
    
    @DELETE
    @Path("/{replyId}")
    public Response completeAppeal(@PathParam("replyId")String identifier) {
        LOG.info("Retrieving a  Reply Resource");
        
        Response response;
        
        try {
            AppealRepresentation finalizedAppealRepresentation = new CompleteappealActivity().completeAppeal(new Identifier(identifier));
            response = Response.ok().entity(finalizedAppealRepresentation).build();
        } catch (AppealAlreadyCompletedException oce) {
            LOG.debug("Appeal already completed");
            response = Response.status(Status.NO_CONTENT).build();
        } catch (NoSuchAppealException nsoe) {
            LOG.debug("No such appeal");
            response = Response.status(Status.NOT_FOUND).build();
        } catch (AppealNotPaidException onpe) {
            LOG.debug("Appeal not sent ");
            response = Response.status(Status.CONFLICT).build();
        }
        
        LOG.debug("The response for the delete reply request is {}", response);
        
        return response;
    }
}
