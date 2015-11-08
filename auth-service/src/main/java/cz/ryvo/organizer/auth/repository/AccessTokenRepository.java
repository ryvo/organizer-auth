package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.AccessTokenVO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccessTokenRepository extends MongoRepository<AccessTokenVO, String> {

    AccessTokenVO findByTokenId(String tokenId);

    long deleteByTokenId(String tokenId);

    List<AccessTokenVO> findAllByClientIdAndUsername(String clientId, String username);

    List<AccessTokenVO> findAllByClientId(String clientId);
}
