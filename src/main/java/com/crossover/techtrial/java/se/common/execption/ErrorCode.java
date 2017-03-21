package com.crossover.techtrial.java.se.common.execption;

/**
 * This class provide standard error codes for the application
 */
public interface ErrorCode {

    String NO_ENOUGH_INV = "No enough inventory";
    String INVALID_OFFER_ROUT = "Invalid offer route";
    String NOT_ENOUGH_CREDIT = "Credit not enough";
    String ROUTE_ALREADY_EXIST = "Rout already exist";
    String CAN_NOT_CONVERT_CURRENCY = "Can not convert currency";
    String USER_NOT_FOUND = "User not found";
    String EMAIL_SENDING_FAIL = "Email sending fail";
    String USER_ALREADY_EXIST = "User already exist";
    String INVALID_INPUT = "Invalid input";
    String ACCOUNT_NOT_EXIST = "Account invalid";
    String MONEY_DEPOSIT_FAIL = "Money deposit fail";
    String FAIL_TO_BUY_TICKET = "Fail to buy ticket";
    String ACCOUNT_OR_ROUT_NOT_FOUND = "Account or Route not found";
    String INVALID_ACCOUNT = "Account not valid for the transaction";
    String INVALIDA_LOGIN = "Invalid Login";
    String AMOUNT_ACCOUNT_EXCHANGE_RATE_NOT_FOUND = "Amount, Account or Exchange rate not found ";
    String AMOUNT_OR_CURRENCY = "Amount or Currency not valid";
    String EMAIL_ALREADY_USED = "Email already used";
    String APPLICANT_NOT_FOUND = "Applicant not found";
}