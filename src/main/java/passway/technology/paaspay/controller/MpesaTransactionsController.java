package passway.technology.paaspay.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import passway.technology.paaspay.exception.Exception;
import passway.technology.paaspay.model.BankCashTransaction;
import passway.technology.paaspay.model.BillpaymentdateUnitamount;
import passway.technology.paaspay.model.ClientDetail;
import passway.technology.paaspay.model.WaterconsumerDetail;
import passway.technology.paaspay.model.MpesaTransaction;
import passway.technology.paaspay.model.Shortcode;
import passway.technology.paaspay.model.WaterBill;
import passway.technology.paaspay.repository.BankCashTransactionRepository;
import passway.technology.paaspay.repository.BillpaymentdateUnitamountRepository;
import passway.technology.paaspay.repository.ClientRepository;
import passway.technology.paaspay.repository.ClientUserRepository;
import passway.technology.paaspay.repository.ConsumerAgentRepository;
import passway.technology.paaspay.repository.MpesaTransactionRepository;
import passway.technology.paaspay.repository.RoleRepository;
import passway.technology.paaspay.repository.ShortcodeRepository;
import passway.technology.paaspay.repository.WaterBillRepository;

@CrossOrigin(origins = "http://watervendors.passwaytechnology.co.ke", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MpesaTransactionsController {
	@Autowired
	WaterBillRepository waterbillRepository;
	
  	@Autowired
  	ClientRepository clientRepository;
  	
  	@Autowired
  	ConsumerAgentRepository consumeragentRepository;
  	
	@Autowired
	ClientUserRepository clientuserRepository;
  
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	ShortcodeRepository shortcodeRepository;

	@Autowired
	BillpaymentdateUnitamountRepository billpaymentdateunitamountRepository;
	
	@Autowired
	MpesaTransactionRepository mpesatransactionRepository;

	@Autowired
	BankCashTransactionRepository bankcashtransactionRepository;
	
	//  @GetMapping("/incomingtransac")
	//  public ResponseEntity<Object> getAllconsumeragentsclients(@Valid @RequestBody MpesaTransaction mpesatransactionRequest) {
//	    try {
//	      List<ConsumerAgent> agentsdetails = new ArrayList<ConsumerAgent>();
//
//	      consumeragentRepository.findAllByConsumerusername(consumername).forEach(agentsdetails::add);
//	      
//	      return new ResponseEntity<>(agentsdetails, HttpStatus.CREATED);
//
//	    } catch (Exception e) {
//			   throw new Exception("Error: Agents details  not found!");}
	    

	//}
	  
	  

	  @GetMapping("/allmpesatransactions/{username}")
	  @PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<List<MpesaTransaction>> getAllmpesatransactions(@PathVariable("username") String username) {
	    try {
	    	
	    	 Optional<ClientDetail> consumeraccountData = clientRepository.findByUsername(username);

			    if (consumeraccountData.isPresent()) {
			    	    ClientDetail existingCustomer = consumeraccountData.get();
			    	    String clientid = existingCustomer.getId();
	    	
//			    	    LocalDate invoicegenerationdates = null;
//			    	    MpesaTransaction clientpaymentdateg = new MpesaTransaction(
//			    	    		invoicegenerationdates,
//				    		    clientid,
//								"200",
//								"JAmes Bong",
//								"Live",
//								"OA&574HJS",
//								"254703434231",
//								invoicegenerationdates,
//								"ODBDHS89",
//								"200",
//								null,
//								null,
//								"Pending Approval"
//								
//						    );
//			    	    mpesatransactionRepository.save(clientpaymentdateg);
			    	    
			    	    
					      List<MpesaTransaction>  mpesadetails= mpesatransactionRepository.findByClientid(clientid);
					      
					      return new ResponseEntity<>(mpesadetails, HttpStatus.CREATED);
			    }else {
			    	   List<MpesaTransaction> mpesadetails = new ArrayList<MpesaTransaction>();
				      return new ResponseEntity<>(mpesadetails, HttpStatus.CREATED);
			    }

	    } catch (Exception e) {
			   throw new Exception("Error: Consumer details  not found!");}
	  }
  	
	  
	  

	  @GetMapping("/approvedmpesatransactionsreport/{username}")
	  @PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<List<MpesaTransaction>> getAllapprovedmpesatransactions(@PathVariable("username") String username) {
	    try {
	    	
			    	    String clientid = username;
					    List<MpesaTransaction>  mpesadetails= mpesatransactionRepository.
					    		findByClientidAndStatus(clientid,"Approved");
					      
					    return new ResponseEntity<>(mpesadetails, HttpStatus.CREATED);
			   
	    } catch (Exception e) {
			   throw new Exception("Error: Consumer details  not found!");}
	  }
	  
	  @GetMapping("/pendingapprovalmpesatransactionsreport/{username}")
	  @PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<List<MpesaTransaction>> getAllpendingapprovalmpesatransactions(@PathVariable("username") String username) {
	    try {
	    	
			    	    String clientid = username;
					    List<MpesaTransaction>  mpesadetails= mpesatransactionRepository.
					    		findByClientidAndStatus(clientid,"Pending Approval");
					      
					    return new ResponseEntity<>(mpesadetails, HttpStatus.CREATED);
			   
	    } catch (Exception e) {
			   throw new Exception("Error: Consumer details  not found!");}
	  }
	
	  
	  @GetMapping("/allcashtransactionsreport/{username}")
	  @PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<List<BankCashTransaction>> allcashtransactions(@PathVariable("username") String username) {
	    try {
	    	
			    	    String clientid = username;
					    List<BankCashTransaction>  mpesadetails= bankcashtransactionRepository.
					    		findByStatusAndTransactiontypeAndClientid("Paid","Cash",clientid);
					      
					    return new ResponseEntity<>(mpesadetails, HttpStatus.CREATED);
			   
	    } catch (Exception e) {
			   throw new Exception("Error: Consumer details  not found!");}
	  }
	  
	  @GetMapping("/allbanktransactionsreport/{username}")
	  @PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<List<BankCashTransaction>> allbanktransactions(@PathVariable("username") String username) {
	    try {
	    	
			    	    String clientid = username;
					    List<BankCashTransaction>  mpesadetails= bankcashtransactionRepository.
					    		findByStatusAndTransactiontypeAndClientid("Paid","Bank",clientid);
					      
					    return new ResponseEntity<>(mpesadetails, HttpStatus.CREATED);
			   
	    } catch (Exception e) {
			   throw new Exception("Error: Consumer details  not found!");}
	  }
}
