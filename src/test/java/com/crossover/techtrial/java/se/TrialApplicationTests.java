package com.crossover.techtrial.java.se;

import com.crossover.techtrial.java.se.configuration.ApplicationProperties;
import com.crossover.techtrial.java.se.dto.account.Account;
import com.crossover.techtrial.java.se.dto.airline.GammaAirlineOffer;
import com.crossover.techtrial.java.se.dto.airline.TicketBuyingRequest;
import com.crossover.techtrial.java.se.dto.user.LoginRequest;
import com.crossover.techtrial.java.se.model.airline.Route;
import com.crossover.techtrial.java.se.model.user.User;
import com.crossover.techtrial.java.se.model.user.UserTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@IntegrationTest("server.port:0")
@ActiveProfiles("test")
public class TrialApplicationTests {

    @Value("${local.server.port}")
    int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    ApplicationProperties applicationProperties;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void contextLoad() {
    }

    @Test
    public void ticketBuyIntegrationTest() {

        User user = getUser();
        createUser(user);
        LoginRequest loginRequest = getLoginRequest();
        String userId = loginToSystem(loginRequest);
        List<GammaAirlineOffer> gammaAirlineOffers = getGammaAirlineOffers(userId);
        assertNotNull(gammaAirlineOffers);
        Account account = getInitialAccount(userId);
        TicketBuyingRequest buyingRequest = getTicketBuyingRequest(account);
        UserTicket userTicket = buyTicket(userId, buyingRequest);
        assertNotNull(userTicket);
        cleanUpDB(userId, account.getId(), userTicket.getId());
        resetExternalSystem();
    }

    private void cleanUpDB(String userId, String accountId, Long userTicketIId) {
        removeAccount(accountId);
        removeUserTicket(userTicketIId);
        removeUser(userId);
    }

    private void removeUser(String userId) {
        String userRemoveQuery = "DELETE from USER WHERE USER_ID =" + userId;
        jdbcTemplate.execute(userRemoveQuery);
    }

    private void removeUserTicket(Long userTicketIId) {
        String userTicketRemoveQuery = "DELETE from USER_TICKET WHERE ID=" + userTicketIId;
        jdbcTemplate.execute(userTicketRemoveQuery);
    }

    private void removeAccount(String accountId) {
        String accountRemoveQuery = "DELETE from USER_ACCOUNT WHERE ACCOUNT_NUMBER='" + accountId + "'";
        jdbcTemplate.execute(accountRemoveQuery);
    }


    private void resetExternalSystem() {
        restTemplate.getForEntity(applicationProperties.getBaseAPIUrl() + applicationProperties.getApplicantId() + "/reset", String.class);
    }

    private UserTicket buyTicket(String userId, TicketBuyingRequest buyingRequest) {
        ResponseEntity<UserTicket> buyResponseEntity = restTemplate.postForEntity("http://localhost:" + port + "/{userId}/gammaairlines/offers/buy", buyingRequest,
                UserTicket.class, userId);
        return buyResponseEntity.getBody();
    }

    private TicketBuyingRequest getTicketBuyingRequest(Account account) {
        TicketBuyingRequest buyingRequest = new TicketBuyingRequest();
        buyingRequest.setAccountId(account.getId());
        buyingRequest.setAmount(2);
        Route route = new Route();
        route.setFrom("London");
        route.setTo("Madrid");
        buyingRequest.setRoute(route);
        return buyingRequest;
    }

    private Account getInitialAccount(String userId) {
        ResponseEntity<Account[]> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/{userId}/paypallets/account/all", Account[].class, userId);
        List<Account> accounts = Arrays.asList(responseEntity.getBody());
        assertNotNull(accounts);
        return accounts.get(0);
    }

    private List<GammaAirlineOffer> getGammaAirlineOffers(String userId) {
        ResponseEntity<GammaAirlineOffer[]> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/{userId}/gammaairlines/offers",
                GammaAirlineOffer[].class, userId);
        GammaAirlineOffer[] offers = responseEntity.getBody();
        List<GammaAirlineOffer> gammaAirlineOffers = Arrays.asList(offers);
        assertNotNull(gammaAirlineOffers);
        return gammaAirlineOffers;
    }

    private String loginToSystem(LoginRequest loginRequest) {
        ResponseEntity<User> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/user/login", loginRequest, User.class);
        User createdUser = responseEntity.getBody();
        assertNotNull(createdUser);
        return createdUser.getUserId().toString();
    }

    private LoginRequest getLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("password");
        return loginRequest;
    }

    private void createUser(User user) {
        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/user/save", user, Boolean.class);
        assertTrue(responseEntity.getBody());
    }

    private User getUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setName("Test");
        user.setPassword("password");
        return user;
    }

}
