package com.crossover.techtrial.java.se.model.airline;

import javax.persistence.*;

/**
 * Created by kasun on 3/1/17.
 */
@Entity
@Table(name = "AIRPORT")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long airportId;

    @Column(name = "AIR_PORT_CODE", nullable = false)
    private String airportCode;

    @Column(name = "AIRPORT_NAME", nullable = false)
    private String airportName;

    @Column(name = "COUNTY", nullable = false)
    private String country;

    public Long getAirportId() {
        return airportId;
    }

    public void setAirportId(Long airportId) {
        this.airportId = airportId;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
