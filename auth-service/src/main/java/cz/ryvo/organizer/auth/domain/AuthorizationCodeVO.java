package cz.ryvo.organizer.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Document(collection = "authentication_code")
public class AuthorizationCodeVO {

    @Id
    private String code;

    private OAuth2Authentication authentication;

    public AuthorizationCodeVO() {
    }

    public AuthorizationCodeVO(String code, OAuth2Authentication authentication) {
        this.code = code;
        this.authentication = authentication;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OAuth2Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = authentication;
    }
}
