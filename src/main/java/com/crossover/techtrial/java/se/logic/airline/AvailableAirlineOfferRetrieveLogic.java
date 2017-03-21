package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dto.airline.GammaAirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.OfferRequest;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class AvailableAirlineOfferRetrieveLogic extends StatelessServiceLogic<List<GammaAirlineOffer>, OfferRequest> {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<GammaAirlineOffer> invoke(OfferRequest offerRequest) {

        validateOfferRequest(offerRequest);
        String availableOfferUrl
                = applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId() + "/gammaairlines/offers";

        try {
            ResponseEntity<GammaAirlineOffer[]> responseEntity = restTemplate.getForEntity(availableOfferUrl, GammaAirlineOffer[].class);
            GammaAirlineOffer[] airlineOffers = responseEntity.getBody();
            return Arrays.asList(airlineOffers);

        } catch (HttpClientErrorException ex) {

            if (ex.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new ServiceRuntimeException(ErrorCode.APPLICANT_NOT_FOUND, "Applicant not found");
            }
        }
        throw new ServiceRuntimeException("Available airline offers loading fail");
    }

    private void validateOfferRequest(OfferRequest offerRequest) {

        ValidationUtil.validate(offerRequest, "Invalid request");
        ValidationUtil.validate(offerRequest.getUserId(), "Invalid User id");
    }
}
