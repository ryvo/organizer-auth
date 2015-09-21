package cz.ryvo.organizer.auth.config;

import cz.ryvo.organizer.auth.service.MongoDbAuthorizationCodeServices;
import cz.ryvo.organizer.auth.service.MongoDbClientDetailsService;
import cz.ryvo.organizer.auth.service.MongoDbTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    MongoDbTokenStore tokenStore;

    @Autowired
    MongoDbAuthorizationCodeServices authorizationCodeServices;

    @Autowired
    MongoDbClientDetailsService clientDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(authorizationCodeServices)
                .allowedTokenEndpointRequestMethods(POST);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(passwordEncoder)
                .checkTokenAccess("hasAuthority('ROLE_CHECK_TOKEN')");
    }
}
