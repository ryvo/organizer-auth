package cz.ryvo.organizer.auth.service;

import cz.ryvo.organizer.auth.domain.AuthorizationCodeVO;
import cz.ryvo.organizer.auth.repository.AuthorizationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.stereotype.Service;

@Service
public class MongoDbAuthorizationCodeServices implements AuthorizationCodeServices {

    private RandomValueStringGenerator generator = new RandomValueStringGenerator(16);

    @Autowired
    AuthorizationCodeRepository authorizationCodeRepository;

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = generator.generate();
        authorizationCodeRepository.save(new AuthorizationCodeVO(code, authentication));
        return code;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        try {
            AuthorizationCodeVO authorizationCode = authorizationCodeRepository.findOne(code);

            if (authorizationCode == null) {
                throw new InvalidGrantException("Invalid authorization code: " + code);
            }

            return authorizationCode.getAuthentication();
        } finally {
            authorizationCodeRepository.delete(code);
        }
    }
}
