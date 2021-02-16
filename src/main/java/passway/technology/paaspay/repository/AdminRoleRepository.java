package passway.technology.paaspay.repository;

import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.AdminRole;
import passway.technology.paaspay.model.ClientDetail;
import passway.technology.paaspay.model.Shortcode;

@JaversSpringDataAuditable
public interface AdminRoleRepository extends MongoRepository<AdminRole, String> {

	  Optional<AdminRole> findByName(String name);

	  Boolean existsByName(String name);
}
