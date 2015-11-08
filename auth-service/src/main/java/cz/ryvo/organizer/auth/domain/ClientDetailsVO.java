package cz.ryvo.organizer.auth.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString(exclude = "clientSecret")
@Document(collection = ClientDetailsVO.COLLECTION)
public class ClientDetailsVO {

    public static final String COLLECTION = "client";

    @Id
    private String clientId;

    private String clientSecret;

    private String resourceIds;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenVaidity;

    private String additionalInformation;

    private String autoapprove;
}
