package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.model.airline.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class AllAirportsLoadingLogic extends StatelessServiceLogic<List<Airport>, Void> {

    @Autowired
    private AirlineDao airlineDao;

    @Transactional
    @Override
    public List<Airport> invoke(Void var) {
        return airlineDao.loadAllAirports();
    }
}
