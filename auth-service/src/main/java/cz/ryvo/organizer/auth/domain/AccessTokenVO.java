package cz.ryvo.organizer.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Data
@AllArgsConstructor
@Document(collection = AccessTokenVO.COLLECTION)
public class AccessTokenVO {

    public static final String COLLECTION = "access_token";

    @Id
    private String authenticationId;

    private OAuth2Authentication authentication;

    //@Indexed
    private String tokenId;

    private DefaultOAuth2AccessToken token;

    private String username;

    private String clientId;

    private String refreshTokenId;
}
