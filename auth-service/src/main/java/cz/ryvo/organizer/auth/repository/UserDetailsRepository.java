package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.UserDetailsVO;
import org.springframework.data.repository.Repository;

public interface UserDetailsRepository extends Repository<UserDetailsVO, String> {

    UserDetailsVO findByUsername(String username);
}
