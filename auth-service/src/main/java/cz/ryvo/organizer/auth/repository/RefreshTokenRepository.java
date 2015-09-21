package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.RefreshTokenVO;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenVO, String> {

    void deleteByRefreshTokenId(String tokenId);
}
