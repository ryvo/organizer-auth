package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.ClientDetailsVO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClientDetailsRepository extends MongoRepository<ClientDetailsVO, String> {
}
