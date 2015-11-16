package cz.ryvo.organizer.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.io.Serializable;

@Document(collection = AuthorizationCodeVO.COLLECTION)
public class AuthorizationCodeVO implements Serializable {

    public static final String COLLECTION = "authorization_code";

    private static final long serialVersionUID = -2493324503371186706L;

    @Id
    private String code;

    @Field("cz/ryvo/organizer/auth")
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
