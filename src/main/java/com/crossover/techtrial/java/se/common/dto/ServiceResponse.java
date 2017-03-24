package com.crossover.techtrial.java.se.common.dto;

/**
 * Standard response type for the provided APIs
 */
public class ServiceResponse<T> {

    private T payload;
    private Error error;

    public boolean hasError () {

        return error != null;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
