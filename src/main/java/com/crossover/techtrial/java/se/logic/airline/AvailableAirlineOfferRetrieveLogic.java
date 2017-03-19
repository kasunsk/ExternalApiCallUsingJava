package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.adapter.airline.AirlineOfferAdapter;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dto.airline.AirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.GammaAirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.OfferRequest;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class AvailableAirlineOfferRetrieveLogic extends StatelessServiceLogic<List<GammaAirlineOffer>, OfferRequest> {

    @Autowired
    private AirlineDao airlineDao;

    @Autowired
    private AirlineOfferAdapter offerAdapter;

    @Autowired
    private AirlineOfferLogicHelper logicHelper;

    @Transactional
    @Override
    public List<GammaAirlineOffer> invoke(OfferRequest offerRequest) {

        validateOfferRequest(offerRequest);
        RestTemplate restTemplate = new RestTemplate();

        String availableOfferUrl
                = "https://api-forest.crossover.com/jEL7J14/gammaairlines/offers";

        ResponseEntity<GammaAirlineOffer[]> responseEntity = restTemplate.getForEntity(availableOfferUrl, GammaAirlineOffer[].class);
        GammaAirlineOffer[] airlineOffers = responseEntity.getBody();
        return Arrays.asList(airlineOffers);
    }

    private void validateOfferRequest(OfferRequest offerRequest) {

        ValidationUtil.validate(offerRequest,"Invalid request");
        ValidationUtil.validate(offerRequest.getApplicantId(), "Invalid applicant id");
    }
}
