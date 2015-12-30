package com.mhchoudh.appeal.activities;

import com.mhchoudh.appeal.model.Identifier;
import com.mhchoudh.appeal.model.Appeal;
import com.mhchoudh.appeal.repositories.AppealRepository;
import com.mhchoudh.appeal.representations.AppealRepresentation;
import com.mhchoudh.appeal.representations.CSE564AppealsUri;

public class ReadAppealActivity {
    public AppealRepresentation retrieveByUri(CSE564AppealsUri appealUri) {
        Identifier identifier  = appealUri.getId();
        
        Appeal appeal = AppealRepository.current().get(identifier);
        
        if(appeal == null) {
            throw new NoSuchAppealException();
        }
        
        return AppealRepresentation.createResponseAppealRepresentation(appeal, appealUri);
    }
}
