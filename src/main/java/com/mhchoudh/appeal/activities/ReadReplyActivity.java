package com.mhchoudh.appeal.activities;

import com.mhchoudh.appeal.model.Identifier;
import com.mhchoudh.appeal.model.AppealStatus;
import com.mhchoudh.appeal.model.AppealFormat;
import com.mhchoudh.appeal.repositories.AppealRepository;
import com.mhchoudh.appeal.repositories.AppealFormatRepository;
import com.mhchoudh.appeal.representations.Link;
import com.mhchoudh.appeal.representations.ReplyRepresentation;
import com.mhchoudh.appeal.representations.Representation;
import com.mhchoudh.appeal.representations.CSE564AppealsUri;

public class ReadReplyActivity {

    public ReplyRepresentation read(CSE564AppealsUri replyUri) {
        Identifier identifier = replyUri.getId();
        if(!appealHasBeenPaid(identifier)) {
            throw new AppealNotPaidException();
        } else if (AppealRepository.current().has(identifier) && AppealRepository.current().get(identifier).getStatus() == AppealStatus.Sent) {
            throw new AppealAlreadyCompletedException();
        }
        
        AppealFormat appealFormat = AppealFormatRepository.current().get(identifier);
        
        return new ReplyRepresentation(appealFormat, new Link(Representation.RELATIONS_URI + "appeal", UriExchange.appealForReply(replyUri)));
    }

    private boolean appealHasBeenPaid(Identifier id) {
        return AppealFormatRepository.current().has(id);
    }

}
