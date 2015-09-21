package cz.ryvo.organizer.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Data
@AllArgsConstructor
@Document(collection = "access_token")
public class AccessTokenVO {

    @Id
    private String authenticationId;

    private OAuth2Authentication authentication;

    @Indexed
    private String tokenId;

    private DefaultOAuth2AccessToken token;
}
