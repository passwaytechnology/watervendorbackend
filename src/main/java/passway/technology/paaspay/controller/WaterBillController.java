package passway.technology.paaspay.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import passway.technology.paaspay.exception.Exception;
import passway.technology.paaspay.model.BankCashTransaction;
import passway.technology.paaspay.model.BillpaymentdateUnitamount;
import passway.technology.paaspay.model.ClientDetail;
import passway.technology.paaspay.model.ConsumerAgent;
import passway.technology.paaspay.model.MpesaTransaction;
import passway.technology.paaspay.model.WaterconsumerDetail;
import passway.technology.paaspay.model.WaterBill;
import passway.technology.paaspay.payload.ErrorResponse;
import passway.technology.paaspay.payload.SuccessResponse;
import passway.technology.paaspay.repository.BankCashTransactionRepository;
import passway.technology.paaspay.repository.BillpaymentdateUnitamountRepository;
import passway.technology.paaspay.repository.ClientRepository;
import passway.technology.paaspay.repository.ClientUserRepository;
import passway.technology.paaspay.repository.ConsumerAgentRepository;
import passway.technology.paaspay.repository.MpesaTransactionRepository;
import passway.technology.paaspay.repository.RoleRepository;
import passway.technology.paaspay.repository.ShortcodeRepository;
import passway.technology.paaspay.repository.WaterBillRepository;
import passway.technology.paaspay.service.ExcelHelper;

@CrossOrigin(origins = "http://watervendors.passwaytechnology.co.ke", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class WaterBillController {


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
	
	  @GetMapping("/allcurrentmonthyearbillinvoices/{username}/{yymm}")
		@PreAuthorize("hasRole('ROLE_USER')")
	//  public ResponseEntity<List<ClientDetail>> getUserconsumerpaymentdate(@PathVariable("username")  String username) {
	public ResponseEntity<Object> getUserconsumermonthlyinvoices(@PathVariable("username") String username,
			@PathVariable("yymm") String yymm) {
		 
		  LocalDate date=null; 
	      Optional<ClientDetail> consumeraccountData = clientRepository.findById(username);

		    if (consumeraccountData.isPresent()) {
		    	  //  ClientDetail existingCustomer = consumeraccountData.get();
		    	  //  String clientid = existingCustomer.getId();
		    		  String clientid=username; 
		    	
		    	    Optional<BillpaymentdateUnitamount> billpaymentdateunitamountData = billpaymentdateunitamountRepository.findByClientid(clientid);
				      if (billpaymentdateunitamountData.isPresent()) {
				    	  
				    	  BillpaymentdateUnitamount existingpaymentdate = billpaymentdateunitamountData.get();
				    	    LocalDate paymentdate = existingpaymentdate.getPaymentdate();
				    	    
				    	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
				    	    String dd = paymentdate.format(formatter);
						    String invoicegenerationdate=yymm+"-"+dd;
						    LocalDate invoicegenerationdates = LocalDate.parse(invoicegenerationdate).minusDays(1);
					

					    //  List<WaterBill> waterbillsdetails = new ArrayList<WaterBill>();
					    //  waterbillRepository.findByClientidAndInvoicegenerationdateAfter(clientid,invoicegenerationdates).forEach(waterbillsdetails::add);
					     // waterbillRepository.findByClientid(clientid).forEach(waterbillsdetails::add);
						    List<WaterBill>  waterbillsdetails=waterbillRepository.findByClientidAndInvoicegenerationdateAfter(clientid,invoicegenerationdates);
					    //  System.out.println(waterbillsdetails); 
					      return new ResponseEntity<>(waterbillsdetails, HttpStatus.CREATED);
				      }else {
				    	  return ResponseEntity
									.badRequest()
									.body(new ErrorResponse("201","Error: Payment Date and Amount per Unit Not Set!."));
					     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				      }
 

		    	 
		
		    } else {
		  
		      return ResponseEntity
						.badRequest()
						.body(new ErrorResponse("201","Error: Account Not Found!."));
		      
		    }
	   
	  }
	  
		
	  @GetMapping("/generatemonthlyinvoices/{username}/{yymm}")
		@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Object> getgenerateconsumermonthlyinvoices(@PathVariable("username") String username,
			@PathVariable("yymm") String yymm) {
		 
		  LocalDate date=null; 
	      Optional<ClientDetail> consumeraccountData = clientRepository.findById(username);

		    if (consumeraccountData.isPresent()) {
//		    	    ClientDetail existingCustomer = consumeraccountData.get();
//		    	    String clientid = existingCustomer.getId();
		    	    String clientid = username;
		    	    
		    	    Optional<BillpaymentdateUnitamount> billpaymentdateunitamountData = billpaymentdateunitamountRepository.findByClientid(clientid);
				      if (billpaymentdateunitamountData.isPresent()) {
				    	  
				    	  BillpaymentdateUnitamount existingpaymentdate = billpaymentdateunitamountData.get();
				    	    LocalDate setpaymentdate = existingpaymentdate.getPaymentdate();
				    	    
				    	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
				    	    String dd = setpaymentdate.format(formatter);
						    String invoicegenerationdate=yymm+"-"+dd;
						    LocalDate invoicegenerationdates = LocalDate.parse(invoicegenerationdate);
						    LocalDate previous_invoicegenerationdates = LocalDate.parse(invoicegenerationdate).minusMonths(1);
						    LocalDate _invoicegenerationdates = LocalDate.parse(invoicegenerationdate).minusDays(1);
						    
						    LocalDate creationdate = LocalDate.now();
						 //  String creationdate=null;
						   String addedby=null;
						   String paidby=null;
						   
						    List<WaterconsumerDetail>  clientdetails=clientuserRepository.findAllByStatusAndClientid("Active",clientid);
						    clientdetails.forEach(name -> {
						         String consumername=  name.getFullname();
						         String meternumber=  name.getMeternumber();
						         String current_reading=  null;
						         String previous_reading;
						         

					Optional<WaterBill> waterbillthismonth = waterbillRepository.findByMeternumberAndClientidAndInvoicegenerationdate(meternumber,clientid,invoicegenerationdates);
					
								    if (!waterbillthismonth.isPresent()) {
								    	
								    	Optional<WaterBill> previouswaterbillthismonth = waterbillRepository.findByMeternumberAndClientidAndInvoicegenerationdate(meternumber,clientid,previous_invoicegenerationdates);
								    	   if (previouswaterbillthismonth.isPresent()) {
								    		   WaterBill previous_readingmeterno = previouswaterbillthismonth.get();
								    		    previous_reading=  previous_readingmeterno.getCurrent_reading();
										    }else {
								    		    previous_reading= null;
								    	    }
								    	
								    	
								    	
								  
									WaterBill clientpaymentdate = new WaterBill(
								    		    clientid,
												consumername,
												meternumber,
												invoicegenerationdates,
												null,
												previous_reading,
												current_reading,
												
												null,
												null,
												null,
												null,
												"Not Paid",
												creationdate,
												addedby,
												paidby
												
										    );
								    waterbillRepository.save(clientpaymentdate);
								    }
						    });
//					      
//					      clientuserRepository.findAll().forEach((clientdetails)->{
//					    	   //String _clientid=clientdetails.getFullname();

//					      });
						    List<WaterBill>  waterbillsdetails=waterbillRepository.findByClientidAndInvoicegenerationdateAfter(clientid,_invoicegenerationdates);
						    
					      return new ResponseEntity<>(waterbillsdetails, HttpStatus.CREATED);
				      }else {
				    	  return ResponseEntity
									.badRequest()
									.body(new ErrorResponse("201","Error: Payment Date and Amount per Unit Not Set!."));
					     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				      }
 

		    	 
		
		    } else {
		  
		      return ResponseEntity
						.badRequest()
						.body(new ErrorResponse("201","Error: Account Not Found!."));
		      
		    }
	   
	  }
	  

	  @PutMapping("/updatewaterconsumerbillssss/{id}/{clientid}")
		@PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<?> updatewaterconsumerbillss(@PathVariable("id") String id,
			  @PathVariable("clientid") String clientid, @RequestBody  WaterBill waterbillRequest) {


	    	Optional<WaterBill> waterbilldetails = waterbillRepository.findByIdAndClientid(id,clientid);
	  	    if (waterbilldetails.isPresent()) {
	  	  	      Float  previous_reading=Float.valueOf(waterbillRequest.getPrevious_reading()).floatValue();
	  	  	      Float  current_reading = Float.valueOf(waterbillRequest.getCurrent_reading()).floatValue();
	    		  Float floatunits=roundFloat(current_reading - previous_reading,2);
	    		  

		    	    Optional<BillpaymentdateUnitamount> billpaymentdateunitamountData = billpaymentdateunitamountRepository.findByClientid(clientid);
				      if (!billpaymentdateunitamountData.isPresent()) {
				    	  
				    	  return ResponseEntity.ok(new ErrorResponse("201","Error: Set Up Payment date"));
				      }
				      
				      BillpaymentdateUnitamount existingpaymentdate = billpaymentdateunitamountData.get();
			    	  String amountperunit = existingpaymentdate.getAmountperunit();

			    	Float floatamountperunit = Float.valueOf(amountperunit).floatValue();
			    	Float totalunitamount=(float) Math.ceil(floatamountperunit*floatunits);
		        	String _totalunitamount = String.valueOf(totalunitamount);

		        	String units = String.valueOf(floatunits);
		        	
		  	    	WaterBill _waterbillpaymentupdateamount = waterbilldetails.get();
		  		    String db_bankref = _waterbillpaymentupdateamount.getBankref();
		  		    String db_paymentstatus = _waterbillpaymentupdateamount.getStatus();
		  		    String db_mpesacode = _waterbillpaymentupdateamount.getMpesacode();
		  		    String db_paymentmethod = _waterbillpaymentupdateamount.getPaymentmethod();
		  		    String db_cashamount = _waterbillpaymentupdateamount.getCashamount();
		  		   
		  		    
		  		    String paymentstatus=waterbillRequest.getStatus();
		  	    	String mpesacode=waterbillRequest.getMpesacode();
		  	    	String paymentmethod=waterbillRequest.getPaymentmethod();
		  	    	String cashamount=waterbillRequest.getCashamount();
		  	    	String bankref=waterbillRequest.getBankref();
		  	    	
		  	        LocalDate creationdate = LocalDate.now();

		  	    	
		  	    	String currentpaidamount;
		  	    	String _mpesacode;
		        	String _cashamount;
		        	String _bankref;
		  	    	if(paymentstatus.equals("Paid")) {
		  	    	
		  	    		if(db_paymentstatus.equals("Paid") || db_paymentstatus.equals("Pending Balance")) {
		  	    			oldwaterbillpayment( db_paymentmethod, db_mpesacode, clientid, creationdate, id);
		  	    			
		  	    		}else {
		  	    			//no old payment was found. or "Old Not Paid"
		  	    		}
		  	    		
		  	    		currentpaidamount=newwaterbillpayment( paymentmethod, mpesacode, 
		  	    				clientid, creationdate, id, cashamount, bankref);
		  	    		
		  	    		if(currentpaidamount.equals("Error")) {
		  					return ResponseEntity.ok(new ErrorResponse("201","Error: "+mpesacode+" That M-pesa code was not found"));
		  				}
		  	    		
		  	    		if(paymentmethod.equals("M-pesa")) {
			        		_mpesacode=mpesacode;
			        		_cashamount=null;
			        		_bankref=null;
			        	}else if(paymentmethod.equals("Cash")) {
			        		_mpesacode=null;
			        		_cashamount=cashamount;
			        		_bankref=null;
			        		
			        	}else {
			        		_mpesacode=null;
			        		_cashamount=cashamount;
			        		_bankref=bankref;
			        	}
		  	    		
		  	    	}else{//not paid on new payment

		  	    		if(db_paymentstatus.equals("Paid") || db_paymentstatus.equals("Pending Balance")) {
		  	    			oldwaterbillpayment( db_paymentmethod, db_mpesacode, clientid, creationdate, id);
		  	    		}	else {
		  	    			//no old payment was found. or "Old Not Paid"
		  	    			
		  	    		}

	  		    		currentpaidamount="0";

		        		_mpesacode=null;
		        		_cashamount=null;
		        		_bankref=null;
		  	    		
		  	    	}
		  	 	//	return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("101",paymentstatus+ "1 Water Bill Updated Successfully!"));


			    	Float floatcurrentpaidamount = Float.valueOf(currentpaidamount).floatValue();
			    	Float floatbalancetotalunitamount=totalunitamount-floatcurrentpaidamount;
		        	String balancetotalunitamount = String.valueOf(floatbalancetotalunitamount);
		        	
		        	String currentstatus;
		        	if(floatbalancetotalunitamount>0) {
		        		currentstatus="Pending Balance";
		        		//compose pending balance message
		        	}else {
		        		 currentstatus="Paid";
		        		 //compose paid message
		        	}
		        	
		        	
		  	    	
		        	_waterbillpaymentupdateamount.setPrevious_reading(waterbillRequest.getPrevious_reading());
		        	_waterbillpaymentupdateamount.setCurrent_reading(waterbillRequest.getCurrent_reading());
		        	_waterbillpaymentupdateamount.setNoofunits(units);
		        	_waterbillpaymentupdateamount.setTotalunitamount(_totalunitamount);
		        	_waterbillpaymentupdateamount.setTotalamountpaid(currentpaidamount);
		        	_waterbillpaymentupdateamount.setTotalamountbalance(balancetotalunitamount);
		        	_waterbillpaymentupdateamount.setPaymentdate(creationdate);
		        	_waterbillpaymentupdateamount.setStatus(currentstatus);
		        	_waterbillpaymentupdateamount.setAddedby("Adm");
		        	_waterbillpaymentupdateamount.setMpesacode(_mpesacode);
		        	_waterbillpaymentupdateamount.setPaymentmethod(paymentmethod);
		        	_waterbillpaymentupdateamount.setCashamount(_cashamount);
		        	_waterbillpaymentupdateamount.setBankref(_bankref);
		        	  
		        	waterbillRepository.save(_waterbillpaymentupdateamount);
		        	
		        	//send sms here
		        
		        	 return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("101","Water Bill Updated Successfully!"));


	  	    } else {
	  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Water consumer Agent was not found!."));
				
	  	    }

	  }
	  
	  public  void oldwaterbillpayment(String db_paymentmethod,String db_mpesacode,String clientid,
			  LocalDate creationdate,String id) {
		  if(db_paymentmethod.equals("M-pesa")) {
    			Optional<MpesaTransaction>  db_mpesadetails= mpesatransactionRepository.findByTransid(db_mpesacode);

					if (db_mpesadetails.isPresent()) {
		    			MpesaTransaction _mpesanewupdate = db_mpesadetails.get();

		    			_mpesanewupdate.setApprovedby(clientid);
		    			_mpesanewupdate.setApprovaldate(creationdate);
		    			_mpesanewupdate.setStatus("Pending Approval");
	  		    		mpesatransactionRepository.save(_mpesanewupdate);
		    		}
			  
			  }else if(db_paymentmethod.equals("Cash")) {
				  
				  Optional<BankCashTransaction>  db_cashdetails= bankcashtransactionRepository.
						  findByStatusAndTransactiontypeAndWaterbillidAndClientid("Paid","Cash",
						  id,clientid);

					if (db_cashdetails.isPresent()) {
						BankCashTransaction _cashupdate = db_cashdetails.get();
						_cashupdate.setAddedby(clientid);
						_cashupdate.setStatus("Trash");
						_cashupdate.setUpdatedate(creationdate);
						bankcashtransactionRepository.save(_cashupdate);
		    		}
				  
			  }else {
				  Optional<BankCashTransaction>  db_bankdetails= bankcashtransactionRepository.
						  findByStatusAndTransactiontypeAndWaterbillidAndClientid("Paid","Bank",
						  id,clientid);

					if (db_bankdetails.isPresent()) {
						BankCashTransaction _bankupdate = db_bankdetails.get();
						_bankupdate.setAddedby(clientid);
						_bankupdate.setStatus("Trash");
						_bankupdate.setUpdatedate(creationdate);
						bankcashtransactionRepository.save(_bankupdate);
		    		}
				  
			  }
	  }

	  public  String newwaterbillpayment(String paymentmethod,String mpesacode,String clientid,
			  LocalDate creationdate,String id,String cashamount,String bankref) {
		 String  currentpaidamount;
			  if(paymentmethod.equals("M-pesa")) {
				Optional<MpesaTransaction>  mpesadetails= mpesatransactionRepository.findByTransid(mpesacode);
	  	    	
			  if(mpesadetails.isPresent()) {
		    			MpesaTransaction _mpesanewupdate = mpesadetails.get();
		  			
		    			_mpesanewupdate.setApprovedby(clientid);
		    			_mpesanewupdate.setApprovaldate(creationdate);
		    			_mpesanewupdate.setStatus("Approved");
  		    		mpesatransactionRepository.save(_mpesanewupdate);
  		    		currentpaidamount=_mpesanewupdate.getTransamount();
				  
			  }else {
				  return "Error";
				//return ResponseEntity.ok(new ErrorResponse("201","Error: "+mpesacode+" That M-pesa code was not found"));
				   
			  }
				   
			  }else if(paymentmethod.equals("Cash")){
				BankCashTransaction bankcashtransactions = new BankCashTransaction(
						clientid,
					id,
					"Cash",
					cashamount,
					null,
					"Paid",
					clientid,
					creationdate
			    );
				bankcashtransactionRepository.save(bankcashtransactions);
				
	    		currentpaidamount=cashamount;
			  }else {
				BankCashTransaction bankcashtransactions = new BankCashTransaction(
						clientid,
					id,
					"Bank",
					cashamount,
					bankref,
					"Paid",
					clientid,
					creationdate
			    );
				bankcashtransactionRepository.save(bankcashtransactions);
				
	    		currentpaidamount=cashamount;
				 
				  
			  }
	     return currentpaidamount;
	  }
	  
	  	@GetMapping("/getconsumerwaterbilltoupdate/{id}/{clientid}")
		@PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<?> getonewaterbillconsumertoupdate(@PathVariable("id") String id,
				  @PathVariable("clientid") String clientid) {
		    try {
		    	Optional<WaterBill> waterbilldetails = waterbillRepository.findByIdAndClientid(id,clientid);

		  	    if (waterbilldetails.isPresent()) {
		  	      return new ResponseEntity<>(waterbilldetails.get(), HttpStatus.OK);

		  	    } else {
		  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Water bill   was not found!."));
					
		  	    }

		    } catch (Exception e) {
				   throw new Exception("Error: Water bill detail  not found!");
				   }
		  }
	  	
//	  	  @GetMapping("/allconsumeragents/{consumername}")
//		  @PreAuthorize("hasRole('ROLE_USER')")
//		  public ResponseEntity<List<ConsumerAgent>> getAllconsumeragentsclients(@PathVariable("consumername") String consumername) {
//		    try {
//		      List<ConsumerAgent> agentsdetails = new ArrayList<ConsumerAgent>();
//
//		      consumeragentRepository.findAllByConsumerusername(consumername).forEach(agentsdetails::add);
//		      
//		      return new ResponseEntity<>(agentsdetails, HttpStatus.CREATED);
//
//		    } catch (Exception e) {
//				   throw new Exception("Error: Agents details  not found!");}
//		    
//		  }
	  
	  
	  @PostMapping("/addimportmonthlyunitsincsvform")
		@PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<?>uploadFile(@RequestParam("username") String username,@RequestParam("file") MultipartFile  file) throws IOException {
	    String message = "";
	    //  String amountperunit ="";
	  //  if (ExcelHelper.hasExcelFormat(file)) { 
	      try {
	    	  

		      Optional<ClientDetail> consumeraccountData = clientRepository.findById(username);

			    if (!consumeraccountData.isPresent()) {
			    	 
			  	  return ResponseEntity.ok(new ErrorResponse("201","Error: Account Not Found"));
				     
			    }
//			    ClientDetail existingCustomer = consumeraccountData.get();
//	    	    String clientid = existingCustomer.getId();
			    String clientid =username;
			    
			    	    Optional<BillpaymentdateUnitamount> billpaymentdateunitamountData = billpaymentdateunitamountRepository.findByClientid(clientid);
					      if (!billpaymentdateunitamountData.isPresent()) {
					    	  
					    	  return ResponseEntity.ok(new ErrorResponse("201","Error: Set Up Payment date"));
					      }
					      
					      BillpaymentdateUnitamount existingpaymentdate = billpaymentdateunitamountData.get();
				    	  String amountperunit = existingpaymentdate.getAmountperunit();
	  
	        List<WaterBill> tutorials = ExcelHelper.excelToData(file.getInputStream());
	        tutorials.forEach(name -> {
		         String id=  name.getId();
		         String previous_reading_old=  name.getPrevious_reading();
	    		 Float  Float_previous_reading_old=   Float.valueOf(previous_reading_old).floatValue();
		         String current_reading_old=  name.getCurrent_reading();
		       //  String units=  name.getNoofunits();
		         
		         Optional<WaterBill> waterbillbyinvoice = waterbillRepository.findById(id);
					
				    if (waterbillbyinvoice.isPresent()) {

			    		   WaterBill previous_readingmeterno = waterbillbyinvoice.get();
					       String _previous_reading=  previous_readingmeterno.getPrevious_reading();
					   		Float  previous_reading;
			    		  //  if(_previous_reading != null || !_previous_reading.isEmpty()) {
			    		   if(_previous_reading != null) {
					    		  previous_reading=   Float.valueOf(_previous_reading).floatValue();
			    		  //  	previous_reading=Float_previous_reading_old;
			    		    }else {
			    		    	previous_reading=Float_previous_reading_old;
			    		    }
			    		   
			    		   Float  current_reading = Float.valueOf(current_reading_old).floatValue();
			    		   
			    		   Float floatunits=roundFloat(current_reading - previous_reading,2);
				    	
				    	Float floatamountperunit = Float.valueOf(amountperunit).floatValue();
				    	Float totalunitamount=(float) Math.ceil(floatamountperunit*floatunits);
			        	String _totalunitamount = String.valueOf(totalunitamount);
			        	
			        	WaterBill  _waterbillpaymentupdateamount = waterbillbyinvoice.get();

			        	String units = String.valueOf(floatunits);
			        	//if( _previous_reading != null || !_previous_reading.isEmpty()) {
			        	if(_previous_reading != null) {
			        	}else {
				        	_waterbillpaymentupdateamount.setPrevious_reading(previous_reading_old);	
			        	}

			        	_waterbillpaymentupdateamount.setCurrent_reading(current_reading_old);
			        	_waterbillpaymentupdateamount.setNoofunits(units);
			        	_waterbillpaymentupdateamount.setTotalunitamount(_totalunitamount);
			        	_waterbillpaymentupdateamount.setTotalamountbalance(_totalunitamount);
			        	  
			        	waterbillRepository.save(_waterbillpaymentupdateamount);	
			         	//SEND SMS HERE;
				    	
				    }
		         
	        
	        });
	        
	//        System.out.println(tutorials);
	   //     repository.saveAll(tutorials);
	      //  message = "Uploaded the file successfully: " + file.getOriginalFilename();

			//	return ResponseEntity.ok(new SuccessResponse("101","Water Bill Updated Successfully!"));

		        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("101","Water Bill Updated Successfully!"));

	        //return ResponseEntity.ok(new SuccessResponse("101","Successfully!"));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + (file).getOriginalFilename() + " ";
	//    message = "Could not upload the file: " +file +"!";
	        System.out.println(message);
	    	return ResponseEntity.ok(new ErrorResponse("201","Error: "+message));
			
	      
	      }
	//    }

//	    message = "Please upload an excel file!";
//	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	  }
	  
	  public  float roundFloat(float f, int places) {
	    	 
	        BigDecimal bigDecimal = new BigDecimal(Float.toString(f));
	        bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
	        return bigDecimal.floatValue();
	    }
	  
	  @GetMapping("/downloadwatermonthlywaterbillexcel/{username}/{yymm}")
		@PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<?> getmonthlywaterbillexcelFile (@PathVariable("username") String username,
				@PathVariable("yymm") String yymm) {
	   
	    LocalDate date=null; 
	      Optional<ClientDetail> consumeraccountData = clientRepository.findById(username);

		    if (consumeraccountData.isPresent()) {
//		    	    ClientDetail existingCustomer = consumeraccountData.get();
//		    	    String clientid = existingCustomer.getId();
		    	  String clientid =username;
		    			  
		    	    Optional<BillpaymentdateUnitamount> billpaymentdateunitamountData = billpaymentdateunitamountRepository.findByClientid(clientid);
				      if (billpaymentdateunitamountData.isPresent()) {
				    	  
				    	  BillpaymentdateUnitamount existingpaymentdate = billpaymentdateunitamountData.get();
				    	    LocalDate setpaymentdate = existingpaymentdate.getPaymentdate();
				    	    
				    	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
				    	    String dd = setpaymentdate.format(formatter);
						    String invoicegenerationdate=yymm+"-"+dd;
						    LocalDate invoicegenerationdates = LocalDate.parse(invoicegenerationdate);
						    LocalDate _invoicegenerationdates = LocalDate.parse(invoicegenerationdate).minusDays(1);
						    
						    LocalDate creationdate = LocalDate.now();
						
						    List<WaterBill>  waterbillsdetails=waterbillRepository.findByClientidAndInvoicegenerationdateAfter(clientid,_invoicegenerationdates);
						    
						    String filename = invoicegenerationdate+" .Date Generated: "+creationdate+".xlsx";
						    

						    ByteArrayInputStream in = ExcelHelper.DataToExcel(waterbillsdetails);
						    
						    InputStreamResource file = new InputStreamResource(in);

						    return ResponseEntity.ok()
						        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
						        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
						        .body(file);
						    
					
						    
						    
						    
				      }else {
				    	  return ResponseEntity
									.badRequest()
									.body(new ErrorResponse("201","Error: Payment Date and Amount per Unit Not Set!."));
					     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				      }


		    	 
		
		    } else {
		  
		      return ResponseEntity
						.badRequest()
						.body(new ErrorResponse("201","Error: Account Not Found!."));
		      
		    }
	    
	    
	  }
	  
	  

	  @GetMapping("/reportpendingwaterbills/{username}")
	  @PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<List<WaterBill>> getAllpendingwaterbills(@PathVariable("username") String username) {
	    try {

	      List<WaterBill>  waterbillsdetails=waterbillRepository.findByClientidAndStatus(username,"Not Paid");
		
		      return new ResponseEntity<>(waterbillsdetails, HttpStatus.CREATED);

	    } catch (Exception e) {
			   throw new Exception("Error: Bill details  not found!");
		}
	    
	    
	    }
	  

	  @GetMapping("/reportpaidwaterbills/{username}")
	  @PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<List<WaterBill>> reportpaidwaterbills(@PathVariable("username") String username) {
	    try {

	      List<WaterBill>  waterbillsdetails=waterbillRepository.findByClientidAndStatus(username,"Paid");
		
		      return new ResponseEntity<>(waterbillsdetails, HttpStatus.CREATED);

	    } catch (Exception e) {
			   throw new Exception("Error: Bill details  not found!");
		}
	    
	    
	    }

	  @GetMapping("/reportbalancewaterbills/{username}")
	  @PreAuthorize("hasRole('ROLE_USER')")
	  public ResponseEntity<List<WaterBill>> reportbalancewaterbills(@PathVariable("username") String username) {
	    try {

	      List<WaterBill>  waterbillsdetails=waterbillRepository.findByClientidAndStatus(username,"Pending Balance");
		
		      return new ResponseEntity<>(waterbillsdetails, HttpStatus.CREATED);

	    } catch (Exception e) {
			   throw new Exception("Error: Bill details  not found!");
		}
	    
	    
	    } 
	    
	
	
}
