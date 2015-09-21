package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.AuthorizationCodeVO;
import org.springframework.data.repository.CrudRepository;

public interface AuthorizationCodeRepository extends CrudRepository<AuthorizationCodeVO, String> {

}
