package com.mhchoudh.appeal.representations;

import com.mhchoudh.appeal.model.AppealFormat;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name = "format", namespace = Representation.APPEALS_NAMESPACE)
public class AppealFormatRepresentation extends Representation {
    
    private static final Logger LOG = LoggerFactory.getLogger(AppealFormatRepresentation.class);
       
    @XmlElement(namespace = AppealFormatRepresentation.APPEALS_NAMESPACE) private double amount;
    @XmlElement(namespace = AppealFormatRepresentation.APPEALS_NAMESPACE) private String cardholderName;
    @XmlElement(namespace = AppealFormatRepresentation.APPEALS_NAMESPACE) private String cardNumber;
    @XmlElement(namespace = AppealFormatRepresentation.APPEALS_NAMESPACE) private String expiryMonth;
    @XmlElement(namespace = AppealFormatRepresentation.APPEALS_NAMESPACE) private int expiryYear;
    

     AppealFormatRepresentation(){
        LOG.debug("In AppealFormat Representation Constructor");
     }
    
    public AppealFormatRepresentation(AppealFormat format, Link...links) {
        LOG.info("Creating an AppealFormat Representation with the format = {} and links = {}", format, links);
        
 //       amount = format.getAmount();
        cardholderName = format.getCardholderName();
        cardNumber = format.getCardNumber();
        expiryMonth = format.getExpiryMonth();
        expiryYear = format.getExpiryYear();
        this.links = java.util.Arrays.asList(links);
        
        LOG.debug("Created the AppealFormat Representation {}", this);
    }

//    public AppealFormatRepresentation(AppealFormat format, Link link, Link link0) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public AppealFormat getFormat() {
        return new AppealFormat(cardholderName, cardNumber, expiryMonth, expiryYear);
    }
    
    public Link getReceiptLink() {
        return getLinkByName(Representation.RELATIONS_URI + "receipt");
    }
    
    public Link getAppealLink() {
        return getLinkByName(Representation.RELATIONS_URI + "appeal");
    }
}
