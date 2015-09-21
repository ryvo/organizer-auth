package cz.ryvo.organizer.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Data
@AllArgsConstructor
@Document(collection = "refresh_token")
public class RefreshTokenVO {

    @Id
    private String tokenId;

    private DefaultOAuth2RefreshToken token;

    private OAuth2Authentication authentication;
}
