package com.mhchoudh.appeal.activities;

import com.mhchoudh.appeal.model.Identifier;
import com.mhchoudh.appeal.model.Appeal;
import com.mhchoudh.appeal.model.AppealStatus;
import com.mhchoudh.appeal.repositories.AppealRepository;
import com.mhchoudh.appeal.representations.Link;
import com.mhchoudh.appeal.representations.AppealRepresentation;
import com.mhchoudh.appeal.representations.Representation;
import com.mhchoudh.appeal.representations.CSE564AppealsUri;

public class CreateAppealActivity {
    public AppealRepresentation create(Appeal appeal, CSE564AppealsUri requestUri) {
        appeal.setStatus(AppealStatus.NotSent);
                
        Identifier identifier = AppealRepository.current().store(appeal);
        
        CSE564AppealsUri appealUri = new CSE564AppealsUri(requestUri.getBaseUri() + "/appeal/" + identifier.toString());
        CSE564AppealsUri AppealsUri = new CSE564AppealsUri(requestUri.getBaseUri() + "/format/" + identifier.toString());
        return new AppealRepresentation(appeal, 
                new Link(Representation.RELATIONS_URI + "cancel", appealUri), 
                new Link(Representation.RELATIONS_URI + "format", AppealsUri), 
                new Link(Representation.RELATIONS_URI + "update", appealUri),
                new Link(Representation.SELF_REL_VALUE, appealUri));
    }
}
