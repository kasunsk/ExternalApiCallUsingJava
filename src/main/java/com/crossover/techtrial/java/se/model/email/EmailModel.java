package com.crossover.techtrial.java.se.model.email;


import com.crossover.techtrial.java.se.model.AbstractTrackableEntity;
import javax.persistence.*;

@Entity
@Table(name = "EMAIL_DATA")
public class EmailModel extends AbstractTrackableEntity {

    public enum EmailStatus {
        SENT, SENDING, FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMAIL_ID", nullable = false)
    private Long emailId;

    @Column(name = "RECEIVER_MAIL", nullable = false)
    private String receiverMailAddress;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private EmailStatus status;

    public Long getEmailId() {
        return emailId;
    }

    public void setEmailId(Long emailId) {
        this.emailId = emailId;
    }

    public String getReceiverMailAddress() {
        return receiverMailAddress;
    }

    public void setReceiverMailAddress(String receiverMailAddress) {
        this.receiverMailAddress = receiverMailAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EmailStatus getStatus() {
        return status;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }
}
