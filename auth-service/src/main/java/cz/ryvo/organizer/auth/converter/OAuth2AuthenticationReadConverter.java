package cz.ryvo.organizer.auth.converter;

import com.mongodb.DBObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.EMPTY_LIST;

@Component
@ReadingConverter
public class OAuth2AuthenticationReadConverter implements Converter<DBObject, OAuth2Authentication> {

    @Override
    public OAuth2Authentication convert(DBObject source) {
        DBObject storedRequest = (DBObject) source.get("storedRequest");
        OAuth2Request oAuth2Request = new OAuth2Request((Map<String, String>) storedRequest.get("requestParameters"),
                (String) storedRequest.get("clientId"), null, true, new HashSet((List) storedRequest.get("scope")), null, null, null, null);
        DBObject userAuthorization = (DBObject) source.get("userAuthentication");
        Object principal = getPrincipalObject(userAuthorization.get("principal"));
        Authentication userAuthentication = new UsernamePasswordAuthenticationToken(principal,
                (String)userAuthorization.get("credentials"), getAuthorities((List) userAuthorization.get("authorities")));
        OAuth2Authentication authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
        return authentication;
    }

    private Object getPrincipalObject(Object principal) {
        if(principal instanceof DBObject) {
            DBObject principalDBObject = (DBObject)principal;

            String username = (String) principalDBObject.get("username");
            String password = principalDBObject.containsField("password") ? (String) principalDBObject.get("password") : "";
            boolean enabled = principalDBObject.containsField("enabled") && (boolean) principalDBObject.get("enabled");
            boolean accountNonExpired = principalDBObject.containsField("accountNonExpired") && (boolean) principalDBObject.get("accountNonExpired");
            boolean accountNonLocked = principalDBObject.containsField("accountNonLocked") && (boolean) principalDBObject.get("accountNonLocked");
            boolean credentialsNonExpired = principalDBObject.containsField("credentialsNonExpired") && (boolean) principalDBObject.get("credentialsNonExpired");
            User user = new User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, EMPTY_LIST);
            return user;
        } else {
            return principal;
        }
    }

    private Collection<GrantedAuthority> getAuthorities(List<Map<String, String>> authorities) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>(authorities.size());
        for(Map<String, String> authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.get("role")));
        }
        return grantedAuthorities;
    }
}
