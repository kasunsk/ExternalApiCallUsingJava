package com.crossover.techtrial.java.se.configuration;

import com.crossover.techtrial.java.se.common.dto.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Autowired
    private Environment environment;

    @Value("${applicant.id}")
    private String applicantId;

    @Value("${initial.deposit.amount}")
    private String initialDepositAmount;

    @Value("${initial.deposit.currency}")
    private String initialDepositCurrency;

    public String getApplicantId() {
        return applicantId;
    }

    public Double getInitialDepositAmount() {
        return Double.parseDouble(initialDepositAmount);
    }

    public Currency getInitialDepositCurrency() {
        return Currency.valueOf(initialDepositCurrency);
    }

}
