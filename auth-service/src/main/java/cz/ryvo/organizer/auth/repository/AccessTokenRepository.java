package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.AccessTokenVO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccessTokenRepository extends CrudRepository<AccessTokenVO, String> {

    AccessTokenVO findByTokenId(String tokenId);

    void deleteByTokenId(String token);

    List<AccessTokenVO> findAllByClientIdAndUsername(String clientId, String username);

    List<AccessTokenVO> findAllByClientId(String clientId);
}
