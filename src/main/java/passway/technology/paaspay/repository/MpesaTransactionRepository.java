package passway.technology.paaspay.repository;

import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.BillpaymentdateUnitamount;
import passway.technology.paaspay.model.MpesaTransaction;
import passway.technology.paaspay.model.WaterBill;

@JaversSpringDataAuditable
public interface MpesaTransactionRepository   extends MongoRepository<MpesaTransaction, String> {
	  Optional<MpesaTransaction> findByTransid(String transid);
	  List<MpesaTransaction> findByClientid(String clientid);
	  List<MpesaTransaction> findByClientidAndStatus(String clientid,String status);
	  
}
