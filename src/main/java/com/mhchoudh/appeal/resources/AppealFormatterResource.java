package com.mhchoudh.appeal.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.mhchoudh.appeal.activities.InvalidSendException;
import com.mhchoudh.appeal.activities.NoSuchAppealException;
import com.mhchoudh.appeal.activities.AppealFormatActivity;
import com.mhchoudh.appeal.activities.UpdateException;
import com.mhchoudh.appeal.model.Identifier;
import com.mhchoudh.appeal.representations.Link;
import com.mhchoudh.appeal.representations.AppealFormatRepresentation;
import com.mhchoudh.appeal.representations.Representation;
import com.mhchoudh.appeal.representations.CSE564AppealsUri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/format/{formattId}")
public class AppealFormatterResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealFormatterResource.class);
    
    private @Context UriInfo uriInfo;
    
    public AppealFormatterResource(){
        LOG.info("Appeal Formatter Resource Constructor");
    }
    
    /**
     * Used in test cases only to allow the injection of a mock UriInfo.
     * @param uriInfo
     */
    public AppealFormatterResource(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    @PUT
    @Consumes("application/vnd.cse564-appeals+xml")
    @Produces("application/vnd.cse564-appeals+xml")
    public Response pay(AppealFormatRepresentation sendRepresentation) {
        LOG.info("Formatting the Appeal");
        
        Response response;
        
        try {
            response = Response.created(uriInfo.getRequestUri()).entity(new AppealFormatActivity().pay(sendRepresentation.getFormat(), 
                            new CSE564AppealsUri(uriInfo.getRequestUri()))).build();
        } catch(NoSuchAppealException nsoe) {
            LOG.debug("No appeal for sending {}", sendRepresentation);
            response = Response.status(Status.NOT_FOUND).build();
        } catch(UpdateException ue) {
            LOG.debug("Invalid update to send {}", sendRepresentation);
            Identifier identifier = new CSE564AppealsUri(uriInfo.getRequestUri()).getId();
            Link link = new Link(Representation.SELF_REL_VALUE, new CSE564AppealsUri(uriInfo.getBaseUri().toString() + "appeal/" + identifier));
            response = Response.status(Status.FORBIDDEN).entity(link).build();
        } catch(InvalidSendException ipe) {
            LOG.debug("Invalid Sending of Appeal");
            response = Response.status(Status.BAD_REQUEST).build();
        } catch(Exception e) {
            LOG.debug("Someting went wrong with sending");
            response = Response.serverError().build();
        }
        
        LOG.debug("Created the new Send activity {}", response);
        
        return response;
    }
}

