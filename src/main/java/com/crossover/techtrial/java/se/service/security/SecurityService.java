package com.crossover.techtrial.java.se.service.security;


import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;

/**
 * Created by kasun on 3/5/17.
 */
public interface SecurityService {

    ServiceResponse<String> encrypt(ServiceRequest<String> word);
}
