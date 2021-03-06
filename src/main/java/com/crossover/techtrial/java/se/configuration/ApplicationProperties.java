package com.crossover.techtrial.java.se.configuration;

import com.crossover.techtrial.java.se.common.dto.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${applicant.id}")
    private String applicantId;

    @Value("${base.api.url}")
    private String baseAPIUrl;

    @Value("${initial.deposit.amount}")
    private String initialDepositAmount;

    @Value("${initial.deposit.currency}")
    private String initialDepositCurrency;

    public String getApplicantId() {
        return applicantId;
    }

    public String getBaseAPIUrl() {
        return baseAPIUrl;
    }

    public Double getInitialDepositAmount() {
        return Double.parseDouble(initialDepositAmount);
    }

    public Currency getInitialDepositCurrency() {
        return Currency.valueOf(initialDepositCurrency);
    }

}
