package passway.technology.paaspay.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;

import passway.technology.paaspay.model.BankCashTransaction;
import passway.technology.paaspay.model.MpesaTransaction;
import passway.technology.paaspay.model.WaterBill;

@JaversSpringDataAuditable
public interface BankCashTransactionRepository   extends MongoRepository<BankCashTransaction, String> {
	
	  Optional<BankCashTransaction> findByStatusAndTransactiontypeAndWaterbillidAndClientid(String status,
			  String transactiontype,
			  String waterbillid,String clientid);
	  

	  List<BankCashTransaction> findByStatusAndTransactiontypeAndClientid(String status,String transactiontype,String clientid);
	  


}
