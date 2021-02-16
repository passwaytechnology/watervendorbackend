package passway.technology.paaspay.repository;

import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.BillpaymentdateUnitamount;

@JaversSpringDataAuditable
public interface BillpaymentdateUnitamountRepository  extends MongoRepository<BillpaymentdateUnitamount, String> {
	  Optional<BillpaymentdateUnitamount> findByClientid(String clientid);


}
