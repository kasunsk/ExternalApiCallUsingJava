package com.crossover.techtrial.java.se.service.user;

import com.crossover.techtrial.java.se.common.dto.*;
import com.crossover.techtrial.java.se.common.dto.Void;
import com.crossover.techtrial.java.se.common.service.RequestAssembler;
import com.crossover.techtrial.java.se.dto.user.LoginRequest;
import com.crossover.techtrial.java.se.logic.user.*;
import com.crossover.techtrial.java.se.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kasun on 1/28/17.
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserSaveLogic userSaveLogic;

    @Autowired
    private AllUsersRetrieveLogic allUsersRetrieveLogic;

    @Autowired
    private UserRemoveLogic userRemoveLogic;

    @Autowired
    private UserLoginLogic userLoginLogic;

    @Autowired
    private UserAuthenticateLogic userAuthenticateLogic;

    @Autowired
    private UserSearchLogic userSearchLogic;

    @Autowired
    private UserLoadById userLoadById;

    @Transactional
    public ServiceResponse<String> saveUser(ServiceRequest<User> user) {

        return RequestAssembler.assemble(userSaveLogic, user);
    }

    @Override
    public ServiceResponse<List<User>> retrieveAllUsers(ServiceRequest<Void> voidServiceRequest) {

        return RequestAssembler.assemble(allUsersRetrieveLogic, voidServiceRequest);
    }

    @Override
    public ServiceResponse<Void> removeUser(ServiceRequest<String> userId) {

        return RequestAssembler.assemble(userRemoveLogic, userId);
    }

    @Override
    public ServiceResponse<User> login(ServiceRequest<LoginRequest> loginRequest) {

        return RequestAssembler.assemble(userLoginLogic, loginRequest);
    }

    @Override
    public ServiceResponse<Void> authenticateUser(ServiceRequest<String> applicantId) {

        return RequestAssembler.assemble(userAuthenticateLogic, applicantId);
    }

    @Override
    public ServiceResponse<User> loadUserById(ServiceRequest<String> applicantId) {

        return RequestAssembler.assemble(userLoadById, applicantId);
    }

    @Override
    public ServiceResponse<List<User>> searchUser(ServiceRequest<UserSearchCriteria> criteria) {

        return RequestAssembler.assemble(userSearchLogic, criteria);
    }
}
