package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.UserDetailsVO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDetailsRepository extends MongoRepository<UserDetailsVO, String> {

    UserDetailsVO findByUsername(String username);
}
