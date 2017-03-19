package com.crossover.techtrial.java.se.dto.email;

import java.io.Serializable;

/**
 * Created by kasun on 3/4/17.
 */
public class EmailParam implements Serializable {

    private String receiverAddress;
    private String subject;
    private String content;

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
