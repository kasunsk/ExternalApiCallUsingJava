package com.crossover.techtrial.java.se.logic.airline;

import com.crossover.techtrial.java.se.common.dto.ServiceRequest;
import com.crossover.techtrial.java.se.common.execption.ServiceRuntimeException;
import com.crossover.techtrial.java.se.common.service.StatelessServiceLogic;
import com.crossover.techtrial.java.se.dao.airline.AirlineDao;
import com.crossover.techtrial.java.se.dao.email.EmailDao;
import com.crossover.techtrial.java.se.dto.email.EmailParam;
import com.crossover.techtrial.java.se.model.email.EmailModel;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import com.crossover.techtrial.java.se.service.email.EmailService;
import com.crossover.techtrial.java.se.service.user.UserService;
import com.crossover.techtrial.java.se.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;


@Component
public class UserTicketEmailSendingLogic extends StatelessServiceLogic<Boolean, String> {

    private static final Logger logger = LoggerFactory.getLogger(UserTicketEmailSendingLogic.class);

    private static String USER_TICKET_MAIL_SUBJECT = "Ticket Details";

    @Autowired
    private EmailDao emailHibernateDao;

    @Autowired
    private AirlineDao airlineHibernateDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Transactional
    @Override
    public Boolean invoke(String userTicketId) {

        UserTicket userTicket = getUserTicket(userTicketId);
        String userId = userTicket.getUserId().toString();
        User user = getUser(userId);
        String emailBody = getContent(userTicket, user);
        String userEmail = user.getEmail();
        EmailModel emailModel = getEmailModel(emailBody, userEmail);
        EmailParam emailParam = getEmailParam(emailBody, userEmail);

        Boolean isMailSent;

        try {
            emailService.sendEmail(new ServiceRequest<>(emailParam));
            logger.info("Email Sent Successfully");
            emailModel.setStatus(EmailModel.EmailStatus.SENT);
            isMailSent = Boolean.TRUE;
        } catch (ServiceRuntimeException ex) {
            logger.error("Email Sending Failed");
            emailModel.setStatus(EmailModel.EmailStatus.FAILED);
            isMailSent = Boolean.FALSE;
        }
        emailHibernateDao.saveEmailData(emailModel);
        return isMailSent;
    }

    private UserTicket getUserTicket(String userTicketId) {

        ValidationUtil.validate(userTicketId, "User ticket id is null");
        return airlineHibernateDao.loadUserTicketById(Long.parseLong(userTicketId));
    }

    private User getUser(String userId) {
        return userService.loadUserById(new ServiceRequest<>(userId)).getPayload();
    }

    private EmailModel getEmailModel(String emailBody, String userEmail) {
        EmailModel emailModel = new EmailModel();
        emailModel.setReceiverMailAddress(userEmail);
        emailModel.setSubject(USER_TICKET_MAIL_SUBJECT);
        emailModel.setStatus(EmailModel.EmailStatus.SENDING);
        emailModel.setContent(emailBody);
        return emailModel;
    }

    private EmailParam getEmailParam(String emailBody, String userEmail) {
        EmailParam emailParam = new EmailParam();
        emailParam.setReceiverAddress(userEmail);
        emailParam.setContent(emailBody);
        emailParam.setSubject(USER_TICKET_MAIL_SUBJECT);
        return emailParam;
    }

    private String getContent(UserTicket userTicket, User user) {

        StringBuilder mailBodyBuilder = new StringBuilder();
        mailBodyBuilder.append(" Passenger Name : ").append(user.getName());
        mailBodyBuilder.append("\n User Ticket Id : ").append(userTicket.getId());
        mailBodyBuilder.append("\n Origin : ").append(userTicket.getOrigin());
        mailBodyBuilder.append("\n Destination : ").append(userTicket.getDestination());
        mailBodyBuilder.append("\n Payment : ").append(userTicket.getPrice()).append(" ").append(userTicket.getCurrency());
        mailBodyBuilder.append("\n Number of Tickets : ").append(userTicket.getTicketsAmount());
        mailBodyBuilder.append("\n\n Thank You, Enjoy our service! ");
        return mailBodyBuilder.toString();
    }
}
