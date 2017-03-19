package com.crossover.techtrial.java.se.common.dto;

/**
 * This can be used when multi tenancy facility enable for the application
 */
public class Tenant {

    private String tenantCode;
    private String tenantName;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
