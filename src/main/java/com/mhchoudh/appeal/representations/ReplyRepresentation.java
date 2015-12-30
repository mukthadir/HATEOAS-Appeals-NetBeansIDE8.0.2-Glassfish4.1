package com.mhchoudh.appeal.representations;

import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.joda.time.DateTime;

import com.mhchoudh.appeal.model.AppealFormat;

@XmlRootElement(name = "reply", namespace = Representation.APPEALS_NAMESPACE)
public class ReplyRepresentation extends Representation {
    
    private static final Logger LOG = LoggerFactory.getLogger(ReplyRepresentation.class);

    @XmlElement(name = "amount", namespace = Representation.APPEALS_NAMESPACE)
    private double amountPaid;
    @XmlElement(name = "paid", namespace = Representation.APPEALS_NAMESPACE)
    private String appealDate;
    
    ReplyRepresentation(){
        LOG.debug("In ReplyRepresentation Constructor");
    } // For JAXB :-(
    
    public ReplyRepresentation(AppealFormat format, Link appealLink) {
        LOG.info("Creating an Reply Representation with the format = {} and links = {}", format, links);
        
//        this.amountPaid = format.getAmount();
        this.appealDate = format.getAppealDate().toString();
        this.links = new ArrayList<Link>();
        links.add(appealLink);
        
        LOG.debug("Created the Reply Representation {}", this);
    }

    public DateTime getPaidDate() {
        return new DateTime(appealDate);
    }
    
    public double getAmountPaid() {
        return amountPaid;
    }

    public Link getOrderLink() {
        return getLinkByName(Representation.RELATIONS_URI + "appeal");
    }
    
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(ReplyRepresentation.class);
            Marshaller marshaller = context.createMarshaller();

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(this, stringWriter);

            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
