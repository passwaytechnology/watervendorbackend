package passway.technology.paaspay.repository;

import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.ERole;
import passway.technology.paaspay.model.Role;


@JaversSpringDataAuditable
public interface RoleRepository  extends MongoRepository<Role, String> {
	  Optional<Role> findByName(ERole name);

}
