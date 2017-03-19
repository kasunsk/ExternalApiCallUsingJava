package com.crossover.techtrial.java.se.service.email;

import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.service.RequestAssembler;
import com.crossover.techtrial.java.se.dto.email.EmailParam;
import com.crossover.techtrial.java.se.logic.email.EmailSendLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailSendLogic emailSendLogic;

    @Override
    public ServiceResponse<Boolean> sendEmail(ServiceRequest<EmailParam> emailParam) {
        return RequestAssembler.assemble(emailSendLogic, emailParam);
    }
}
