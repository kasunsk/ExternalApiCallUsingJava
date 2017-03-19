package com.crossover.techtrial.java.se.common.dto;

/**
 * Standard response type for the provided APIs
 */
public class ServiceResponse<T> {

    private T payload;
    private Error error;

    public boolean hasError () {

        if (error != null) {
            return true;
        }
        return false;
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
