package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.adapter.airline.AirlineOfferAdapter;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dto.airline.AirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.OfferRequest;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class AvailableAirlineOfferRetrieveLogic extends StatelessServiceLogic<List<AirlineOffer>, OfferRequest> {

    @Autowired
    private AirlineDao airlineDao;

    @Autowired
    private AirlineOfferAdapter offerAdapter;

    @Autowired
    private AirlineOfferLogicHelper logicHelper;

    @Transactional
    @Override
    public List<AirlineOffer> invoke(OfferRequest offerRequest) {

        validateOfferRequest(offerRequest);
        logicHelper.authenticateApplicant(offerRequest.getApplicantId());
        List<AirlineOfferModel> offerList = airlineDao.loadAirlineOffers(offerRequest.getStatus());
        return offerAdapter.adaptFromModelList(offerList);
    }

    private void validateOfferRequest(OfferRequest offerRequest) {

        ValidationUtil.validate(offerRequest,"Invalid request");
        ValidationUtil.validate(offerRequest.getApplicantId(), "Invalid applicant id");
    }
}
