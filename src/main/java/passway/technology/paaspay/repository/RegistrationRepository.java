package passway.technology.paaspay.repository;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.User;

@JaversSpringDataAuditable
public interface RegistrationRepository extends MongoRepository<User, String>{

}
