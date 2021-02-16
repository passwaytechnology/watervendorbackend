package passway.technology.paaspay.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import passway.technology.paaspay.model.ClientDetail;
import passway.technology.paaspay.model.WaterBill;

@JaversSpringDataAuditable
public interface WaterBillRepository  extends MongoRepository<WaterBill, String> {
	 // Optional<WaterBill> findByClientid(String clientid);
	  Optional<WaterBill> findByConsumerid(String consumerid);
	  Optional<WaterBill> findById(String id);
	  Optional<WaterBill> findByIdAndClientid(String id,String clientid);
	  

	  Optional<WaterBill> findByMeternumberAndClientidAndInvoicegenerationdate(String meternumber,String clientid,
			  LocalDate invoicegenerationdate);
	  
	  List<WaterBill> findByInvoicegenerationdateBetween(LocalDate startinvoicegenerationdate,LocalDate endinvoicegenerationdate);
	  List<WaterBill> findByClientid(String clientid);
	  
	  List<WaterBill> findByClientidAndStatus(String clientid,String status);
	//  @Query("SELECT new com.javatechie.jpa.dto.OrderResponse(c.name , p.productName) FROM Customer c JOIN c.products p")
	  List<WaterBill> findByClientidAndInvoicegenerationdateAfter(String clientid,LocalDate invoicegenerationdate);
	  
	  //findByStartDateAfter
}
