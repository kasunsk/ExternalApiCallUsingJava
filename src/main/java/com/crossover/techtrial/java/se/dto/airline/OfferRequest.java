package com.crossover.techtrial.java.se.dto.airline;

/**
 * Created by kasun on 2/4/17.
 */
public class OfferRequest {

    private String applicantId;
    private AirlineOffer.AirlineOfferStatus status;

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public AirlineOffer.AirlineOfferStatus getStatus() {
        return status;
    }

    public void setStatus(AirlineOffer.AirlineOfferStatus status) {
        this.status = status;
    }
}
