package com.crossover.techtrial.java.se.adapter.airline;


import com.crossover.techtrial.java.se.common.dto.Price;
import com.crossover.techtrial.java.se.dto.airline.AirlineOffer;
import com.crossover.techtrial.java.se.model.airline.AirlineOfferModel;
import com.crossover.techtrial.java.se.model.airline.Route;
import org.mockito.InjectMocks;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

public class AirlineOfferAdapterUnitTest {

    @InjectMocks
    AirlineOfferAdapter adapter = new AirlineOfferAdapter();

    @Test
    public void adaptFromDtoNullTest() {
        assertNull(adapter.adaptFromDto(null));
    }

    @Test
    public void adaptFromDtoTest() {

        AirlineOffer airlineOffer = new AirlineOffer();
        airlineOffer.setRoute(new Route());
        airlineOffer.setPrice(new Price());
        assertNotNull(adapter.adaptFromDto(airlineOffer));
    }

    @Test
    public void adaptFromModelListTest() {

        List<AirlineOfferModel> airlineOfferModels = new ArrayList<>();
        AirlineOfferModel airlineOfferModel = new AirlineOfferModel();
        airlineOfferModels.add(airlineOfferModel);
        List<AirlineOffer> airlineOffers = adapter.adaptFromModelList(airlineOfferModels);
        assertNotNull(airlineOffers);
        assertEquals(airlineOffers.size(), 1);
    }
}
