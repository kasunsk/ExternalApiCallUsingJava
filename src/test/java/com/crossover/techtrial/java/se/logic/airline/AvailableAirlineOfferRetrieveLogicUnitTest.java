package com.crossover.techtrial.java.se.logic.airline;


import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dto.airline.GammaAirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.OfferRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class AvailableAirlineOfferRetrieveLogicUnitTest {

    @InjectMocks
    private AvailableAirlineOfferRetrieveLogic logic = new AvailableAirlineOfferRetrieveLogic();

    @Mock
    RestTemplate restTemplate;

    @Mock
    ApplicationProperties applicationProperties;


    @BeforeMethod(alwaysRun = true)
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void validateTest() {
        logic.invoke(null);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invalidApplicantTest() {
        logic.invoke(new OfferRequest());
    }

    @Test
    public void invokeTest() {

        when(applicationProperties.getBaseAPIUrl()).thenReturn("www.crosover.com/");
        when(applicationProperties.getApplicantId()).thenReturn("aaaa");

        GammaAirlineOffer gammaAirlineOfferOne = new GammaAirlineOffer();
        GammaAirlineOffer[] gammaAirlineOffers = new GammaAirlineOffer[]{gammaAirlineOfferOne};
        ResponseEntity<GammaAirlineOffer[]> responseEntity = new ResponseEntity<>(gammaAirlineOffers, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(GammaAirlineOffer[].class))).thenReturn(responseEntity);

        OfferRequest offerRequest = getOfferRequest();
        List<GammaAirlineOffer> result = logic.invoke(offerRequest);
        assertEquals(result.size(), 1);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeApplicantNotFoundFailTest() {

        when(applicationProperties.getBaseAPIUrl()).thenReturn("www.crosover.com/");
        when(applicationProperties.getApplicantId()).thenReturn("aaaa");

        when(restTemplate.getForEntity(anyString(), eq(GammaAirlineOffer[].class))).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        OfferRequest offerRequest = getOfferRequest();
        logic.invoke(offerRequest);
    }

    @Test(expectedExceptions = ServiceRuntimeException.class)
    public void invokeFailTest() {

        when(applicationProperties.getBaseAPIUrl()).thenReturn("www.crosover.com/");
        when(applicationProperties.getApplicantId()).thenReturn("aaaa");

        when(restTemplate.getForEntity(anyString(), eq(GammaAirlineOffer[].class))).thenThrow(new HttpClientErrorException(HttpStatus.FAILED_DEPENDENCY));

        OfferRequest offerRequest = getOfferRequest();
        logic.invoke(offerRequest);
    }

    private OfferRequest getOfferRequest() {
        OfferRequest offerRequest = new OfferRequest();
        offerRequest.setUserId("12");
        return offerRequest;
    }
}
