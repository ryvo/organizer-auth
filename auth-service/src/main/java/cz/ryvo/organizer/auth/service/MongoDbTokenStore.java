package cz.ryvo.organizer.auth.service;

import cz.ryvo.organizer.auth.domain.AccessTokenVO;
import cz.ryvo.organizer.auth.domain.RefreshTokenVO;
import cz.ryvo.organizer.auth.generator.DefaultTokenKeyGenerator;
import cz.ryvo.organizer.auth.generator.TokenKeyGenerator;
import cz.ryvo.organizer.auth.repository.AccessTokenRepository;
import cz.ryvo.organizer.auth.repository.RefreshTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class MongoDbTokenStore implements TokenStore {

    private static final Logger log = LoggerFactory.getLogger(MongoDbTokenStore.class);

    private TokenKeyGenerator tokenKeyGenerator = new DefaultTokenKeyGenerator();

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        String tokenId = tokenKeyGenerator.extractKey(token);
        AccessTokenVO accessToken = accessTokenRepository.findByTokenId(tokenId);

        if (accessToken == null) {
            log.info("Failed to find access token " + token);
            return null;
        }

        return accessToken.getAuthentication();
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        removeAccessToken(token);

        AccessTokenVO accessToken = new AccessTokenVO(authenticationKeyGenerator.extractKey(authentication),
                authentication, tokenKeyGenerator.extractAccessTokenKey(token), (DefaultOAuth2AccessToken) token,
                authentication.isClientOnly() ? null : authentication.getName(), authentication.getOAuth2Request().getClientId(),
                tokenKeyGenerator.extractRefreshTokenKey(token.getRefreshToken()));

        accessTokenRepository.save(accessToken);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        String tokenId = tokenKeyGenerator.extractAccessTokenKey(token);
        accessTokenRepository.deleteByTokenId(tokenId);
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken token, OAuth2Authentication authentication) {
        RefreshTokenVO refreshToken = new RefreshTokenVO(tokenKeyGenerator.extractRefreshTokenKey(token), (DefaultOAuth2RefreshToken) token, authentication);

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        String tokenId = tokenKeyGenerator.extractKey(tokenValue);

        RefreshTokenVO refreshToken = refreshTokenRepository.findOne(tokenId);

        if (refreshToken == null) {
            log.info("Failed to find refresh token " + refreshToken);
            return null;
        }

        return refreshToken.getToken();
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        String tokenId = tokenKeyGenerator.extractRefreshTokenKey(token);

        RefreshTokenVO refreshToken = refreshTokenRepository.findOne(tokenId);

        if (refreshToken == null) {
            log.info("Failed to find refresh token " + refreshToken);
            return null;
        }

        return refreshToken.getAuthentication();
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        refreshTokenRepository.delete(tokenKeyGenerator.extractRefreshTokenKey(token));
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken token) {
        refreshTokenRepository.deleteByTokenId(tokenKeyGenerator.extractRefreshTokenKey(token));
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        String authenticationId = authenticationKeyGenerator.extractKey(authentication);
        AccessTokenVO accessToken = accessTokenRepository.findOne(authenticationId);

        if (accessToken == null) {
            log.info("Failed to find access token for client id " + authentication.getOAuth2Request().getClientId());
        }

        return accessToken.getToken();
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        List<AccessTokenVO> accessTokens = accessTokenRepository.findAllByClientIdAndUsername(clientId, userName);
        return accessTokens.stream().map(AccessTokenVO::getToken).collect(toList());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        List<AccessTokenVO> accessTokens = accessTokenRepository.findAllByClientId(clientId);
        return accessTokens.stream().map(AccessTokenVO::getToken).collect(toList());
    }
}
