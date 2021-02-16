package passway.technology.paaspay.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.WaterBill;
import passway.technology.paaspay.model.WaterconsumerDetail;
import org.javers.spring.annotation.JaversSpringDataAuditable;

@JaversSpringDataAuditable
public interface ClientUserRepository  extends MongoRepository<WaterconsumerDetail, String> {
	  Optional<WaterconsumerDetail> findByMeternumber(String meternumber);
	  Optional<WaterconsumerDetail> findById(String id);
	  Optional<WaterconsumerDetail> findByIdAndClientid(String id,String clientid);
	  Boolean existsByMeternumber(String meternumber);

	  Boolean existsByEmail(String email);
	  List<WaterconsumerDetail> findAllByStatusOrderByFullnameAsc(String status);
	  List<WaterconsumerDetail> findAllByStatusAndClientid(String status,String clientid);
	  List<WaterconsumerDetail> findAllByMeternumber(String meternumber);
	  
	  
}
