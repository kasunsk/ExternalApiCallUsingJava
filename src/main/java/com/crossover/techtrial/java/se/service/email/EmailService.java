package com.crossover.techtrial.java.se.service.email;


import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.dto.email.EmailParam;

public interface EmailService {

    ServiceResponse<Boolean> sendEmail(ServiceRequest<EmailParam> emailParam);
}
