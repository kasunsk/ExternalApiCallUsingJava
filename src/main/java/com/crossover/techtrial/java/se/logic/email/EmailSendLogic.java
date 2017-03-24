package com.crossover.techtrial.java.se.logic.email;

import com.crossover.techtrial.java.se.common.execption.ErrorCode;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dto.email.EmailParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailSendLogic extends StatelessServiceLogic<Boolean, EmailParam> {

    @Autowired
    private Environment environment;

    private static String senderEmail;
    private static String senderPassword;

    @PostConstruct
    public void init() {
        senderEmail = environment.getRequiredProperty("email.sender.address");
        senderPassword = environment.getRequiredProperty("email.sender.password");
    }

    @Override
    public Boolean invoke(EmailParam emailParam) {

        Properties props = getProperties();

        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            Message emailMessage = buildEmailMessage(emailParam.getReceiverAddress(), emailParam.getSubject(), emailParam.getContent(),
                    session);
            Transport.send(emailMessage);
        } catch (Exception ex) {
            throw new ServiceRuntimeException(ErrorCode.EMAIL_SENDING_FAIL, ex.getMessage());
        }

        return Boolean.TRUE;
    }

    private Message buildEmailMessage(String receiverEmail, String emailSubject, String emailBody, Session session) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setText(emailBody);
        msg.setSubject(emailSubject);
        msg.setFrom(new InternetAddress(senderEmail));
        msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress(receiverEmail));
        return msg;
    }

    private Properties getProperties() {

        Properties props = new Properties();
        props.put("mail.smtp.user", senderEmail);
        props.put("mail.smtp.host", environment.getRequiredProperty("email.smtp.server"));
        props.put("mail.smtp.port", environment.getRequiredProperty("email.server.port"));
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", environment.getRequiredProperty("email.server.port"));
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return props;
    }

    private static class SMTPAuthenticator extends javax.mail.Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    }
}
