package com.mhchoudh.appeal.model;

import org.joda.time.DateTime;

public class AppealFormat {

//    private final double amount;
    private final String emailReceipient;
    private final String emailSender;
    private final String subject;
    private final int bodyOfEmail;
    private DateTime appealDate;

    public AppealFormat(String emailReceipient, String cardNumber, String expiryMonth, int expiryYear) {
//        this.amount = amount;
        this.emailReceipient = emailReceipient;
        this.emailSender = cardNumber;
        this.subject = expiryMonth;
        this.bodyOfEmail = expiryYear;
        appealDate = new DateTime();
    }
    

    public String getCardholderName() {
        return emailReceipient;
    }

    public String getCardNumber() {
        return emailSender;
    }

    public String getExpiryMonth() {
        return subject;
    }

    public int getExpiryYear() {
        return bodyOfEmail;
    }
    public DateTime getAppealDate() {
        return appealDate;
    }
}