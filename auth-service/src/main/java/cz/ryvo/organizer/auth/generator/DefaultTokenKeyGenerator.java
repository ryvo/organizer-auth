package cz.ryvo.organizer.auth.generator;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DefaultTokenKeyGenerator implements TokenKeyGenerator {

    @Override
    public String extractAccessTokenKey(OAuth2AccessToken token) {
        return extractKey(token.getValue());
    }

    @Override
    public String extractRefreshTokenKey(OAuth2RefreshToken token) {
        return extractKey(token.getValue());
    }

    @Override
    public String extractKey(String value) {
        if (value == null) {
            return null;
        }

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }
}
