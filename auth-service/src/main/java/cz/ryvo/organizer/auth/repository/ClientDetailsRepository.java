package cz.ryvo.organizer.auth.repository;

import cz.ryvo.organizer.auth.domain.ClientDetailsVO;
import org.springframework.data.repository.CrudRepository;

public interface ClientDetailsRepository extends CrudRepository<ClientDetailsVO, String> {
}
