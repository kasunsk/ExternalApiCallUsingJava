package com.crossover.techtrial.java.se.logic.user;

import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dto.user.LoginRequest;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.security.SecurityService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserLoginLogic extends StatelessServiceLogic<User, LoginRequest> {

    @Autowired
    private UserLogicHelper logicHelper;

    @Autowired
    private SecurityService securityService;

    @Transactional
    @Override
    public User invoke(LoginRequest loginRequest) {

        validateLoginRequest(loginRequest);
        User user = logicHelper.loadUser(loginRequest.getEmail());
        validateLogin(loginRequest, user);
        user.setUserBankAccounts(null);
        return user;
    }

    private void validateLoginRequest(LoginRequest loginRequest) {

        ValidationUtil.validate(loginRequest, "Invalid login request");
        ValidationUtil.validate(loginRequest.getEmail(), "Email is null");
        ValidationUtil.validate(loginRequest.getPassword(), "Password can not be null");
    }

    private void validateLogin(LoginRequest loginRequest, User user) {

        String encryptedPassword = securityService.encrypt(new ServiceRequest<>(loginRequest.getPassword())).getPayload();

        if (!user.getPassword().equals(encryptedPassword)) {
            throw new ServiceRuntimeException("Login invalid");
        }
    }
}
