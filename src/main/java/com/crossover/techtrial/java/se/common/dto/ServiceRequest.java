package com.crossover.techtrial.java.se.common.dto;

/**
 * Standard generic request type for provided APIs. Usefull for multi tenancy capability
 */
public class ServiceRequest<T> {

    private T payload;
    private Tenant tenant;

    public ServiceRequest(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
