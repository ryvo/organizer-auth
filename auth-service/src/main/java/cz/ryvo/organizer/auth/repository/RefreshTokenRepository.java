package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.RefreshTokenVO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepository extends MongoRepository<RefreshTokenVO, String> {

    long deleteByTokenId(String tokenId);
}
