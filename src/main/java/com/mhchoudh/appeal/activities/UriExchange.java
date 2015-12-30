package com.mhchoudh.appeal.activities;

import com.mhchoudh.appeal.representations.CSE564AppealsUri;

public class UriExchange {

    public static CSE564AppealsUri formatingAppeal(CSE564AppealsUri appealUri) {
        checkForValidAppealUri(appealUri);
        return new CSE564AppealsUri(appealUri.getBaseUri() + "/format/" + appealUri.getId().toString());
    }
    
    public static CSE564AppealsUri appealForFormatting(CSE564AppealsUri formatUri) {
        checkForValidFormatUri(formatUri);
        return new CSE564AppealsUri(formatUri.getBaseUri() + "/appeal/" + formatUri.getId().toString());
    }

    public static CSE564AppealsUri responseForAppeal(CSE564AppealsUri formatUri) {
        checkForValidFormatUri(formatUri);
        return new CSE564AppealsUri(formatUri.getBaseUri() + "/reply/" + formatUri.getId().toString());
    }
    
    public static CSE564AppealsUri appealForReply(CSE564AppealsUri replyUri) {
        checkForValidReplyUri(replyUri);
        return new CSE564AppealsUri(replyUri.getBaseUri() + "/appeal/" + replyUri.getId().toString());
    }

    private static void checkForValidAppealUri(CSE564AppealsUri appealUri) {
        if(!appealUri.toString().contains("/appeal/")) {
            throw new RuntimeException("Invalid Appeal URI");
        }
    }
    
    private static void checkForValidFormatUri(CSE564AppealsUri format) {
        if(!format.toString().contains("/format/")) {
            throw new RuntimeException("Invalid Format URI");
        }
    }
    
    private static void checkForValidReplyUri(CSE564AppealsUri reply) {
        if(!reply.toString().contains("/reply/")) {
            throw new RuntimeException("Invalid Reply URI");
        }
    }
}
