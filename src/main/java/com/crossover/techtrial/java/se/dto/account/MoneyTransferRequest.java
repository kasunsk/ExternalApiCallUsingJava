package com.crossover.techtrial.java.se.dto.account;


import com.crossover.techtrial.java.se.common.dto.Currency;

public class MoneyTransferRequest {

    public enum TransferType {
        DEPOSIT, WITHDRAW
    }

    private String accountNumber;
    private Double transferAmount;
    private Currency currency;
    private TransferType transferType;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }
}
