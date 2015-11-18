package cz.ryvo.organizer.auth;

import com.jayway.restassured.RestAssured;
import cz.ryvo.organizer.auth.config.MongoDbTestConfig;
import cz.ryvo.organizer.auth.domain.ClientDetailsVO;
import cz.ryvo.organizer.auth.domain.UserDetailsVO;
import cz.ryvo.organizer.auth.repository.ClientDetailsRepository;
import cz.ryvo.organizer.auth.repository.UserDetailsRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OrganizerAuthApplication.class)
@Import({MongoDbTestConfig.class})
@WebIntegrationTest(randomPort = true)
@TestPropertySource("classpath:application-test.properties")
public abstract class IntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
        prepareData();
    }

    private void prepareData() {
        ClientDetailsVO clien1 = new ClientDetailsVO();
        clien1.setClientId("test_client_with_client_credentials");
        clien1.setClientSecret("$2a$10$aRoiSKjLwJ9B4lMCVG.BjuEqcZVCzyPz8YjbiSqs1Ghz4k6zevEI2");
        clien1.setResourceIds("calendar");
        clien1.setAuthorizedGrantTypes("client_credentials");
        clien1.setAuthorities("ROLE_CHECK_TOKEN");
        clien1.setScope("test_client_with_client_credentials-scope");
        clientDetailsRepository.save(clien1);

        ClientDetailsVO client2 = new ClientDetailsVO();
        client2.setClientId("test_client_with_password");
        client2.setClientSecret("$2a$10$t7LMC8kIu64tnp2FerkYj.sA35sETs2PBakQTq8.yEwAP5DC8ATs6");
        client2.setResourceIds("calendar");
        client2.setAuthorizedGrantTypes("password");
        client2.setAuthorities(null);
        client2.setScope("test_client_with_password-scope");
        clientDetailsRepository.save(client2);

        UserDetailsVO user1 = new UserDetailsVO();
        user1.setUsername("test-user");
        user1.setPassword("$2a$10$erx46laAODrCNXQVY64VHel./in3vz2Zo0.vrBPjs0gvlijERLWlC");
        user1.setEnabled(true);
        user1.setLocked(false);
        userDetailsRepository.save(user1);
    }
}
