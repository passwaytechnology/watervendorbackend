package passway.technology.paaspay.repository;

import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.ClientDetail;
import passway.technology.paaspay.model.ConsumerAgent;

@JaversSpringDataAuditable
public interface ConsumerRepository extends MongoRepository<ConsumerAgent, String> {
	  Optional<ClientDetail> findByUsername(String username);

	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
	  List<ClientDetail> findAllByStatusOrderByFullnameAsc(String status);
	  List<ClientDetail> findAllByUsername(String username);
}
