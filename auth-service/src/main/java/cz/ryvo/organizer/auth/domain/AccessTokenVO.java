package cz.ryvo.organizer.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Document(collection = AccessTokenVO.COLLECTION)
public class AccessTokenVO implements Serializable {

    public static final String COLLECTION = "access_token";

    private static final long serialVersionUID = -5394518100891175303L;

    @Id
    private String authenticationId;

    private OAuth2Authentication authentication;

    @Indexed
    private String tokenId;

    private DefaultOAuth2AccessToken token;

    private String username;

    private String clientId;

    private String refreshTokenId;
}
