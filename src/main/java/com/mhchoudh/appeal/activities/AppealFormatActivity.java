package com.mhchoudh.appeal.activities;

import com.mhchoudh.appeal.model.Identifier;
import com.mhchoudh.appeal.model.AppealStatus;
import com.mhchoudh.appeal.model.AppealFormat;
import com.mhchoudh.appeal.repositories.AppealRepository;
import com.mhchoudh.appeal.repositories.AppealFormatRepository;
import com.mhchoudh.appeal.representations.Link;
import com.mhchoudh.appeal.representations.AppealFormatRepresentation;
import com.mhchoudh.appeal.representations.Representation;
import com.mhchoudh.appeal.representations.CSE564AppealsUri;

public class AppealFormatActivity {
    public AppealFormatRepresentation pay(AppealFormat format, CSE564AppealsUri formatUri) {
        Identifier identifier = formatUri.getId();
        
        // Don't know the appeal!
        if(!AppealRepository.current().has(identifier)) {
            throw new NoSuchAppealException();
        }
        
        // Already paid
        if(AppealFormatRepository.current().has(identifier)) {
            throw new UpdateException();
        }
        
        AppealRepository.current().get(identifier).setStatus(AppealStatus.Reviewing);
                AppealFormatRepository.current().store(identifier, format);

        
        return new AppealFormatRepresentation(format, new Link(Representation.RELATIONS_URI + "appeal", UriExchange.appealForFormatting(formatUri)),
                new Link(Representation.RELATIONS_URI + "reply", UriExchange.responseForAppeal(formatUri)));
    }
}
