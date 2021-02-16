package passway.technology.paaspay.repository;

import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.ConsumerAgent;
import passway.technology.paaspay.model.WaterconsumerDetail;

@JaversSpringDataAuditable
public interface ConsumerAgentRepository extends MongoRepository<ConsumerAgent, String> {
	  Optional<ConsumerAgent> findByUsername(String username);

	  Boolean existsByUsername(String username);
	  
	  boolean existsById(String id);

	  Boolean existsByEmail(String email);
	  List<ConsumerAgent> findAll();
	  List<ConsumerAgent> findAllByUsername(String username);
	  List<ConsumerAgent> findAllByConsumerusername(String consumername);
	  List<ConsumerAgent> findAllByConsumerusernameAndAgenttype(String consumername,String agenttype);

	  Optional<ConsumerAgent> findByIdAndConsumerusername(String id,String consumerusername);
}
