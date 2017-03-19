package com.crossover.techtrial.java.se.service.security;

import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.ServiceResponse;
import com.crossover.techtrial.java.se.common.service.RequestAssembler;
import com.crossover.techtrial.java.se.logic.security.DataEncryptLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private DataEncryptLogic dataEncryptLogic;

    @Override
    public ServiceResponse<String> encrypt(ServiceRequest<String> word) {
        return RequestAssembler.assemble(dataEncryptLogic, word);
    }
}
