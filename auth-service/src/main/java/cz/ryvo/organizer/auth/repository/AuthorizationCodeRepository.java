package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.AuthorizationCodeVO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorizationCodeRepository extends MongoRepository<AuthorizationCodeVO, String> {

}
