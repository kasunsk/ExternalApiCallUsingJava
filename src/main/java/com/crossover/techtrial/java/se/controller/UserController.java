package com.crossover.techtrial.java.se.controller;

import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.dto.UserSearchCriteria;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.dto.user.LoginRequest;
import com.crossover.techtrial.java.se.dto.user.UserRole;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class will provide http rest interface for the User Services
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    @ResponseBody
    public List<User> allUsers() {
        List<User> users = userService.retrieveAllUsers(new ServiceRequest<>(new com.crossover.techtrial.java.se.common.dto.Void())).getPayload();
        return users;
    }

    @RequestMapping(value = {"/remove/{userId}"}, method = RequestMethod.GET)
    @ResponseBody
    public Boolean removeUser(@PathVariable("userId") String userId) {
        userService.removeUser(new ServiceRequest<>(userId));
        return Boolean.TRUE;
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    @ResponseBody
    public User login(@RequestBody LoginRequest loginRequest) {

        return userService.login(new ServiceRequest<>(loginRequest)).getPayload();
    }

    @RequestMapping(value = {"/{applicantId}/search"}, method = RequestMethod.POST)
    @ResponseBody
    public List<User> searchUser(@PathVariable("applicantId") String applicantId, @RequestBody UserSearchCriteria searchCriteria) {

        User user = userService.loadUserById(new ServiceRequest<>(applicantId)).getPayload();

        if (user.getRole() != UserRole.ADMIN) {
            throw new ServiceRuntimeException("User not have enough privileges");
        }
        return userService.searchUser(new ServiceRequest<>(searchCriteria)).getPayload();
    }


    @RequestMapping(value = {"/save"}, method = RequestMethod.POST)
    @ResponseBody
    public Boolean saveUser(@RequestBody User user) {
        userService.saveUser(new ServiceRequest<>(user));
        return Boolean.TRUE;
    }

}
