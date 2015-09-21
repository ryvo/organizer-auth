package cz.ryvo.organizer.auth.service;

import cz.ryvo.organizer.auth.domain.ClientDetailsVO;
import cz.ryvo.organizer.auth.repository.ClientDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

import static cz.ryvo.organizer.auth.cache.Caches.CLIENT_DETAILS;
import static java.util.Arrays.stream;
import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.toSet;

@Service
public class MongoDbClientDetailsService implements ClientDetailsService {

    @Autowired
    ClientDetailsRepository clientDetailsRepository;

    @Override
    @Cacheable(CLIENT_DETAILS)
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        ClientDetailsVO clientDetails = clientDetailsRepository.findOne(clientId);
        return null;
    }

    private ClientDetails convert(ClientDetailsVO clientDetailsVO) {
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(clientDetailsVO.getClientId());
        clientDetails.setClientSecret(clientDetailsVO.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(clientDetailsVO.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(clientDetailsVO.getRefreshTokenVaidity());
        clientDetails.setScope(parseStringCollection(clientDetailsVO.getScope()));
        clientDetails.setAuthorities(parseStringCollection(clientDetailsVO.getAuthorities()).stream().map(SimpleGrantedAuthority::new).collect(toSet()));
        clientDetails.setAuthorizedGrantTypes(parseStringCollection(clientDetailsVO.getAuthorizedGrantTypes()));
        clientDetails.setRegisteredRedirectUri(singleton(clientDetailsVO.getWebServerRedirectUri()));
        clientDetails.setResourceIds(parseStringCollection(clientDetailsVO.getResourceIds()));

        return clientDetails;
    }

    private Set<String> parseStringCollection(String stringCollection) {
        if (stringCollection == null) {
            return emptySet();
        }

        return stream(StringUtils.split(stringCollection, ",")).map(String::trim).sorted().collect(toSet());
    }
}
