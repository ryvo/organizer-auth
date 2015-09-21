package cz.ryvo.organizer.auth.generator;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

public interface TokenKeyGenerator {

    String extractAccessTokenKey(OAuth2AccessToken token);

    String extractRefreshTokenKey(OAuth2RefreshToken token);

    String extractKey(String value);
}
