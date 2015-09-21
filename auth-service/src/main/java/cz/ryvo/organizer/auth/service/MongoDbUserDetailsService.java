package cz.ryvo.organizer.auth.service;

import cz.ryvo.organizer.auth.domain.UserDetailsVO;
import cz.ryvo.organizer.auth.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class MongoDbUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsVO userDetailsVO = userDetailsRepository.findByUsername(username);
        if (userDetailsVO == null) {
            throw new UsernameNotFoundException("User with username " + username + "not found");
        }
        return convert(userDetailsVO);
    }

    private UserDetails convert(UserDetailsVO userDetailsVO) {
        return new User(
                userDetailsVO.getUsername(),
                userDetailsVO.getPassword(),
                userDetailsVO.isEnabled(),
                userDetailsVO.isExpired(),
                userDetailsVO.isCredentialsExpired(),
                userDetailsVO.isLocked(),
                emptyList()
        );
    }

}
