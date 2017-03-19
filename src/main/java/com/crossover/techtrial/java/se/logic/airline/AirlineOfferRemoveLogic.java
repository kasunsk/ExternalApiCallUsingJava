package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * This logic class will provide airline offer remove functionality
 */
@Component
public class AirlineOfferRemoveLogic extends StatelessServiceLogic<Void, String> {

    @Autowired
    private AirlineDao airlineDao;

    @Transactional
    @Override
    public Void invoke(String offerId) {

        ValidationUtil.validate(offerId, "Invalid offer id");
        airlineDao.remove(offerId);
        return new Void();
    }
}
