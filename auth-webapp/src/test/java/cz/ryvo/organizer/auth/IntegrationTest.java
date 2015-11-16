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
        ClientDetailsVO clientDetailsVO = new ClientDetailsVO();
        clientDetailsVO.setClientId("test-client");
        clientDetailsVO.setClientSecret("$2a$10$GcOJjvXV8zRYx0HMAb9fYe7yLkZT0kF1CDX.50B.H6YQYLAeil4U2");
        clientDetailsVO.setResourceIds("calendar");
        clientDetailsVO.setAuthorizedGrantTypes("password,client_credentials");
        clientDetailsVO.setAuthorities("ROLE_CHECK_TOKEN");
        clientDetailsVO.setScope("organizer-web-scope");
        clientDetailsRepository.save(clientDetailsVO);

        UserDetailsVO userDetailsVO = new UserDetailsVO();
        userDetailsVO.setUsername("test-user");
        userDetailsVO.setPassword("$2a$10$erx46laAODrCNXQVY64VHel./in3vz2Zo0.vrBPjs0gvlijERLWlC");
        userDetailsVO.setEnabled(true);
        userDetailsVO.setLocked(false);
        userDetailsRepository.save(userDetailsVO);
    }
}
