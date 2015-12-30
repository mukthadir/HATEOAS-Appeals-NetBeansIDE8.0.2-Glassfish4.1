package com.mhchoudh.appeal.representations;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mhchoudh.appeal.activities.InvalidAppealException;
import com.mhchoudh.appeal.activities.UriExchange;
import com.mhchoudh.appeal.model.ComposeAppeal;
import com.mhchoudh.appeal.model.Appeal;
import com.mhchoudh.appeal.model.AppealStatus;
import static com.mhchoudh.appeal.representations.Representation.RELATIONS_URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "appeal", namespace = Representation.APPEALS_NAMESPACE)
public class AppealRepresentation extends Representation {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealRepresentation.class);

    @XmlElement(name = "item", namespace = Representation.APPEALS_NAMESPACE)
    private List<ComposeAppeal> items;
    @XmlElement(name = "cost", namespace = Representation.APPEALS_NAMESPACE)
    private double cost;
    @XmlElement(name = "status", namespace = Representation.APPEALS_NAMESPACE)
    private AppealStatus status;

    /**
     * For JAXB :-(
     */
    AppealRepresentation() {
        LOG.debug("In AppealRepresentation Constructor");
    }

    public static AppealRepresentation fromXmlString(String xmlRepresentation) {
        LOG.info("Creating an Appeal object from the XML = {}", xmlRepresentation);
                
        AppealRepresentation appealRepresentation = null;     
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            appealRepresentation = (AppealRepresentation) unmarshaller.unmarshal(new ByteArrayInputStream(xmlRepresentation.getBytes()));
        } catch (Exception e) {
            throw new InvalidAppealException(e);
        }
        
        LOG.debug("Generated the object {}", appealRepresentation);
        return appealRepresentation;
    }
    
    public static AppealRepresentation createResponseAppealRepresentation(Appeal appeal, CSE564AppealsUri appealUri) {
        LOG.info("Creating a Response appeal for appeal = {} and appeal URI", appeal.toString(), appealUri.toString());
        
        AppealRepresentation appealRepresentation;     
        
        CSE564AppealsUri AppealsUri = new CSE564AppealsUri(appealUri.getBaseUri() + "/format/" + appealUri.getId().toString());
        LOG.debug("Appeal Format URI = {}", AppealsUri);
        
        if(appeal.getStatus() == AppealStatus.NotSent) {
            LOG.debug("The appeal status is {}", AppealStatus.NotSent);
            appealRepresentation = new AppealRepresentation(appeal, 
                    new Link(RELATIONS_URI + "cancel", appealUri), 
                    new Link(RELATIONS_URI + "format", AppealsUri), 
                    new Link(RELATIONS_URI + "update", appealUri),
                    new Link(Representation.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.Reviewing) {
            LOG.debug("The appeal status is {}", AppealStatus.Reviewing);
            appealRepresentation = new AppealRepresentation(appeal, new Link(Representation.SELF_REL_VALUE, appealUri));
        } else if(appeal.getStatus() == AppealStatus.Ready) {
            LOG.debug("The appeal status is {}", AppealStatus.Ready);
            appealRepresentation = new AppealRepresentation(appeal, new Link(Representation.RELATIONS_URI + "reciept", UriExchange.responseForAppeal(AppealsUri)));
        } else if(appeal.getStatus() == AppealStatus.Sent) {
            LOG.debug("The appeal status is {}", AppealStatus.Sent);
            appealRepresentation = new AppealRepresentation(appeal);            
        } else {
            LOG.debug("The appeal status is in an unknown status");
            throw new RuntimeException("Unknown appeal Status");
        }
        
        LOG.debug("The appeal representation created for the Create Response appeal Representation is {}", appealRepresentation);
        
        return appealRepresentation;
    }

    public AppealRepresentation(Appeal appeal, Link... links) {
        LOG.info("Creating an Appeal Representation for appeal = {} and links = {}", appeal.toString(), links.toString());
        
        try {
            this.items = appeal.getItems();
            this.status = appeal.getStatus();
            this.links = java.util.Arrays.asList(links);
        } catch (Exception ex) {
            throw new InvalidAppealException(ex);
        }
        
        LOG.debug("Created the AppealRepresentation {}", this);
    }

    @Override
    public String toString() {
        LOG.info("Converting Appeal Representation object to string");
        try {
            JAXBContext context = JAXBContext.newInstance(AppealRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Appeal getAppeal() {
        LOG.info("Retrieving the Appeal Representation");
        LOG.debug("Location = {}", items);
        if (items == null) {
            throw new InvalidAppealException();
        }
        for (ComposeAppeal i : items) {
            if (i == null) {
                throw new InvalidAppealException();
            }
        }
        
        Appeal appeal = new Appeal(status, items);
        
        LOG.debug("Retrieving the Appeal Representation {}", appeal);

        return appeal;
    }

    public Link getCancelLink() {
        LOG.info("Retrieving the Cancel link ");
        return getLinkByName(RELATIONS_URI + "cancel");
    }

    public Link getFormatLink() {
        LOG.info("Retrieving the Format link ");
        return getLinkByName(RELATIONS_URI + "format");
    }

    public Link getUpdateLink() {
        LOG.info("Retrieving the Update link ");
        return getLinkByName(RELATIONS_URI + "update");
    }

    public Link getSelfLink() {
        LOG.info("Retrieving the Self link ");
        return getLinkByName("self");
    }
    
    public AppealStatus getStatus() {
        LOG.info("Retrieving the appeal status {}", status);
        return status;
    }

    public double getCost() {
        LOG.info("Retrieving the appeal cost {}", cost);
        return cost;
    }
}
