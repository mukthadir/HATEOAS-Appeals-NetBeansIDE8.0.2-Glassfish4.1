package com.mhchoudh.appeal.activities;

import com.mhchoudh.appeal.model.Identifier;
import com.mhchoudh.appeal.model.Appeal;
import com.mhchoudh.appeal.model.AppealStatus;
import com.mhchoudh.appeal.repositories.AppealRepository;
import com.mhchoudh.appeal.representations.AppealRepresentation;

public class CompleteappealActivity {

    public AppealRepresentation completeAppeal(Identifier id) {
        AppealRepository repository = AppealRepository.current();
        if (repository.has(id)) {
            Appeal appeal = repository.get(id);

            if (appeal.getStatus() == AppealStatus.Ready) {
                appeal.setStatus(AppealStatus.Sent);
            } else if (appeal.getStatus() == AppealStatus.Sent) {
                throw new AppealAlreadyCompletedException();
            } else if (appeal.getStatus() == AppealStatus.NotSent) {
                throw new AppealNotPaidException();
            }

            return new AppealRepresentation(appeal);
        } else {
            throw new NoSuchAppealException();
        }
    }
}
