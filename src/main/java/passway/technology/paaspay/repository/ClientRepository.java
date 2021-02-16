package passway.technology.paaspay.repository;

import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.ClientDetail;
import passway.technology.paaspay.model.Role;


@JaversSpringDataAuditable
public interface ClientRepository extends MongoRepository<ClientDetail, String> {
	  Optional<ClientDetail> findByUsername(String username);
	  Optional<ClientDetail> findById(String id);
	  
	 // Optional<ClientDetail> findById(String id);
	  
	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
	  List<ClientDetail> findAllByStatusOrderByFullnameAsc(String status);
	  List<ClientDetail> findAllByStatusAndRoles(String status,Role adminRole);
	  List<ClientDetail> findAllByUsername(String username);
	  
}
