package com.mhchoudh.appeal.activities;

import com.mhchoudh.appeal.model.Identifier;
import com.mhchoudh.appeal.model.Appeal;
import com.mhchoudh.appeal.model.AppealStatus;
import com.mhchoudh.appeal.repositories.AppealRepository;
import com.mhchoudh.appeal.representations.AppealRepresentation;
import com.mhchoudh.appeal.representations.CSE564AppealsUri;

public class RemoveAppealActivity {
    public AppealRepresentation delete(CSE564AppealsUri appealUri) {
        // Discover the URI of the appeal that has been cancelled
        
        Identifier identifier = appealUri.getId();

        AppealRepository appealRepository = AppealRepository.current();

        if (appealRepository.appealNotPlaced(identifier)) {
            throw new NoSuchAppealException();
        }

        Appeal appeal = appealRepository.get(identifier);

        // Can't delete a ready or preparing appeal
        if (appeal.getStatus() == AppealStatus.Reviewing || appeal.getStatus() == AppealStatus.Ready) {
            throw new AppealDeletionException();
        }

        if(appeal.getStatus() == AppealStatus.NotSent) { // An unpaid appeal is being cancelled 
            appealRepository.remove(identifier);
        }

        return new AppealRepresentation(appeal);
    }

}
