package passway.technology.paaspay.repository;

import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.ClientDetail;
import passway.technology.paaspay.model.Role;
import passway.technology.paaspay.model.User;

@JaversSpringDataAuditable
public interface UserRepository  extends MongoRepository<User, String> {
	  Optional<User> findByUsername(String username);

	  Boolean existsByUsername(String username);

	  Boolean existsByEmail(String email);
	  
	  
	  List<User> findAllByRoles(Role adminRole);
	  
}
