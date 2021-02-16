package passway.technology.paaspay.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.type.Date;

import passway.technology.paaspay.model.AdminRole;
import passway.technology.paaspay.model.BillpaymentdateUnitamount;
import passway.technology.paaspay.model.ClientDetail;
import passway.technology.paaspay.model.WaterconsumerDetail;
import passway.technology.paaspay.model.ConsumerAgent;
import passway.technology.paaspay.model.ERole;
import passway.technology.paaspay.model.Role;
import passway.technology.paaspay.model.Shortcode;
import passway.technology.paaspay.model.TrashTable;
import passway.technology.paaspay.model.User;
import passway.technology.paaspay.model.WaterBill;
import passway.technology.paaspay.payload.ErrorResponse;
import passway.technology.paaspay.payload.SuccessResponse;
import passway.technology.paaspay.repository.AdminRoleRepository;
import passway.technology.paaspay.repository.BillpaymentdateUnitamountRepository;
import passway.technology.paaspay.repository.ClientRepository;
import passway.technology.paaspay.repository.ClientUserRepository;
import passway.technology.paaspay.repository.ConsumerAgentRepository;
import passway.technology.paaspay.repository.RoleRepository;
import passway.technology.paaspay.repository.ShortcodeRepository;
import passway.technology.paaspay.repository.TrashTableRepository;
import passway.technology.paaspay.repository.UserRepository;
import passway.technology.paaspay.exception.Exception;




@CrossOrigin(origins = "http://watervendors.passwaytechnology.co.ke", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ClientController {

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
		TrashTableRepository 	trashTableRepository;

		@Autowired
		AdminRoleRepository 	adminroleRepository;
		
		
		@Autowired
		UserRepository 	userRepository;
		
		  @PostMapping("/addshortcode")
			@PreAuthorize("hasRole('ROLE_ADMIN')")
			public ResponseEntity<?> registershortcode(@Valid @RequestBody Shortcode shortcodeRequest) {
			  
				Set<ClientDetail> clientdetail = new HashSet<>();
				
				ClientDetail clientaccountData = clientRepository.findByUsername(shortcodeRequest.getClient())
						.orElseThrow(()-> new Exception(" Error: Client name is not found"));
				clientdetail.add(clientaccountData);

				
				if (shortcodeRepository.existsByShortcodeAndClientdetail(shortcodeRequest.getShortcode(),
						clientaccountData)) {
					 return ResponseEntity
								.badRequest()
								.body(new ErrorResponse("201","Error: Shortcode already exists with that client."));
				}
			
				Shortcode shortc = new Shortcode(
						shortcodeRequest.getShortcode()
						);
				shortc.setClientdetail(clientdetail);
				
				shortcodeRepository.save(shortc);

				return ResponseEntity.ok(new SuccessResponse("101","Shortcode registered successfully!"));
			}
		  
		  @GetMapping("/allshortcodes")
			@PreAuthorize("hasRole('ROLE_ADMIN')")
		  public ResponseEntity<List<Shortcode>> getAllShortcodes(@RequestParam(required = false) String shortcodedetail) {
		    try {
		     List<Shortcode> shortcodedetails = new ArrayList<Shortcode>();
	
		     shortcodeRepository.findAll().forEach(shortcodedetails::add);

		      
		      return new ResponseEntity<>(shortcodedetails, HttpStatus.CREATED);

		    } catch (Exception e) {
				   throw new Exception("Error: Shortcode details  not found!");}
		  }
		  
		  @GetMapping("/alladminusers")
			@PreAuthorize("hasRole('ROLE_ADMIN')")
		  public ResponseEntity<List<User>> getAlladminusers(@RequestParam(required = false) String shortcodedetail) {
		    try {
		     List<User> details = new ArrayList<User>();
		     
		     Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(()-> new Exception("Error: Role is not found"));

		     userRepository.findAllByRoles(adminRole).forEach(details::add);
	
		     

		      
		      return new ResponseEntity<>(details, HttpStatus.CREATED);

		    } catch (Exception e) {
				   throw new Exception("Error: Shortcode details  not found!");}
		  }
		  
		  
		  @GetMapping("/alladminroles")
			@PreAuthorize("hasRole('ROLE_ADMIN')")
		  public ResponseEntity<List<AdminRole>> getAllRoles(@RequestParam(required = false) String roledetail) {
		    try {
		     List<AdminRole> roledetails = new ArrayList<AdminRole>();
	
		     adminroleRepository.findAll().forEach(roledetails::add);

		      
		      return new ResponseEntity<>(roledetails, HttpStatus.CREATED);

		    } catch (Exception e) {
				   throw new Exception("Error: Role detail  not found!");}
		  }
		  
		    
		    @PostMapping("/addrole")
			@PreAuthorize("hasRole('ROLE_ADMIN')")
			public ResponseEntity<?> registernewrole(@Valid @RequestBody AdminRole adminroleRequest) {
			  
				if (adminroleRepository.existsByName(adminroleRequest.getName())) {
					 return ResponseEntity
								.badRequest()
								.body(new ErrorResponse("201","Error: Role was found."));
				}
			
				AdminRole adminrole = new AdminRole(
						adminroleRequest.getName()
						);
				
				adminroleRepository.save(adminrole);

				return ResponseEntity.ok(new SuccessResponse("101","Role registered successfully!"));
			}
		    
		    
		  	@GetMapping("/getroletoupdate/{id}")
			@PreAuthorize("hasRole('ROLE_ADMIN')")
			  public ResponseEntity<?> getoneadmindetailss(@PathVariable("id") String id) {
			    try {
			    	Optional<AdminRole> roledetails = adminroleRepository.findById(id);

			  	    if (roledetails.isPresent()) {
			  	      return new ResponseEntity<>(roledetails.get(), HttpStatus.OK);

			  	    } else {
			  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Role was not found!."));
						
			  	    }

			    } catch (Exception e) {
					   throw new Exception("Error: Role details  not found!");
					   }
			  }
		  	
		  	
			  @PutMapping("/updateroledetailssss/{id}")
			  @PreAuthorize("hasRole('ROLE_ADMIN')")
			  public ResponseEntity<?> updaterolesssdetaills(@PathVariable("id") String id,
					  @RequestBody  AdminRole adminroleRequest) {


				    Optional<AdminRole> roledetails = adminroleRepository.findById(id);

			  	    if (roledetails.isPresent()) {
			  	    	AdminRole _updateLoad = roledetails.get();
			  	    	_updateLoad.setName(adminroleRequest.getName());

						
	    
	    return new ResponseEntity<>(adminroleRepository.save(_updateLoad), HttpStatus.OK);

			  	    } else {
			  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Role details  not found!."));
						
			  	    }

			  }

		  @DeleteMapping("/deleteclientshortcoorde/{id}")
			@PreAuthorize("hasRole('ROLE_ADMIN')")
		  public ResponseEntity<?> deleteclientshortcode(@PathVariable("id") String id) {


		    	Optional<Shortcode> clientshortcodedetails = 
		    			shortcodeRepository.findById(id);

		  	    if (clientshortcodedetails.isPresent()) {
		  	    	
			
			    	
		  	    	shortcodeRepository.deleteById(id);

					return ResponseEntity.ok(new SuccessResponse("101","Client shortcode Deleted Successfully"));	
		  	    } else {
		  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Client shortcode  was not found!."));
					
		  	    }

		  }
		  
		  
		  @GetMapping("/allactiveclientforshortcodes")
			@PreAuthorize("hasRole('ROLE_ADMIN')")
		  public ResponseEntity<List<ClientDetail>> getAllActiveClientShortcodes(@RequestParam(required = false) String shortcodedetail) {
		    try {
		 

		      List<ClientDetail> clientdetails = new ArrayList<ClientDetail>();
		      

				Role adminRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(()-> new Exception("Error: Role is not found"));

		      clientRepository.findAllByStatusAndRoles("Active",adminRole).forEach(clientdetails::add);
		      

		      return new ResponseEntity<>(clientdetails, HttpStatus.CREATED);

		    } catch (Exception e) {
				   throw new Exception("Error: Shortcode details  not found!");}
		  }
		  
		  
		  	@GetMapping("/getoneclientdetailss/{id}")
			@PreAuthorize("hasRole('ROLE_ADMIN')")
			  public ResponseEntity<?> getoneclientdetailss(@PathVariable("id") String id) {
			    try {
			    	Optional<ClientDetail> clientdetails = clientRepository.findById(id);

			  	    if (clientdetails.isPresent()) {
			  	      return new ResponseEntity<>(clientdetails.get(), HttpStatus.OK);

			  	    } else {
			  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Client was not found!."));
						
			  	    }

			    } catch (Exception e) {
					   throw new Exception("Error: Client details  not found!");
					   }
			  }
		  
		  	
		  	
			  @PutMapping("/update__clientdetails/{id}")
				@PreAuthorize("hasRole('ROLE_ADMIN')")
			  public ResponseEntity<?> updateclientdetaills(@PathVariable("id") String id,
					  @RequestBody  ClientDetail clientRequest) {


				    Optional<ClientDetail> clientdetails = clientRepository.findById(id);

			  	    if (clientdetails.isPresent()) {
			  	    	ClientDetail _updateLoad = clientdetails.get();
			  	    	_updateLoad.setFullname(clientRequest.getFullname());
			  	    	_updateLoad.setUsername(clientRequest.getUsername());
				    	_updateLoad.setPhonenumber(clientRequest.getPhonenumber());
				    	_updateLoad.setEmail(clientRequest.getEmail());
				    	_updateLoad.setLocation(clientRequest.getLocation());
				    	_updateLoad.setModule(clientRequest.getModule());
				    	_updateLoad.setStatus(clientRequest.getStatus());
				 
				    	String password = clientRequest.getPassword();

						if (password != "") {
							_updateLoad.setPassword(encoder.encode(password));
						}

						
	    
	    return new ResponseEntity<>(clientRepository.save(_updateLoad), HttpStatus.OK);

			  	    } else {
			  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Client details  not found!."));
						
			  	    }

			  }
		  	

		  @GetMapping("/allusershortcodes/{username}")
			@PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<List<Shortcode>> getUserAllShortcodes(@PathVariable("username")  String username) {
		    try {
		      List<Shortcode> clientdetails = new ArrayList<Shortcode>();

		      
				
				ClientDetail clientaccountData = clientRepository.findById(username)
						.orElseThrow(()-> new Exception(" Error: Client name is not found"));
				
				shortcodeRepository.findAllByClientdetail(clientaccountData).forEach(clientdetails::add);
		      
		      return new ResponseEntity<>(clientdetails, HttpStatus.CREATED);

		    } catch (Exception e) {
				   throw new Exception("Error: Shortcode details  not found!");}
		  }
		  
		  

		  @GetMapping("/allconsumerpaymentdateunitamount/{username}")
			@PreAuthorize("hasRole('ROLE_USER')")
		//  public ResponseEntity<List<ClientDetail>> getUserconsumerpaymentdate(@PathVariable("username")  String username) {
		public ResponseEntity<Object> getUserconsumerpaymentdate(@PathVariable("username") String username) {
				   
			 
		      Optional<ClientDetail> consumeraccountData = clientRepository.findByUsername(username);

			    if (consumeraccountData.isPresent()) {
			    	    ClientDetail existingCustomer = consumeraccountData.get();
			    	    String clientid = existingCustomer.getId();

					      Optional<BillpaymentdateUnitamount> billpaymentdateunitamountData = billpaymentdateunitamountRepository.findByClientid(clientid);
					      if (billpaymentdateunitamountData.isPresent()) {
					    	  
						      return new ResponseEntity<>(billpaymentdateunitamountData.get(), HttpStatus.OK);
					      }else {
					    	  return ResponseEntity
										.badRequest()
										.body(new ErrorResponse("201","Error: Payment Date and Amount per Unit Not Set!."));
						     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
					      }
			    	 
			
			    } else {
			    //  return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			      return ResponseEntity
							.badRequest()
							.body(new ErrorResponse("201","Error: Payment Date and Amount per Unit Not Set!."));
			      
			    }
		      
		 

		  }
		   
			
		  @PostMapping("/addclientpaymentdateunitamount")
			@PreAuthorize("hasRole('ROLE_USER')")
			public ResponseEntity<?> registerpaymentdateunitamount(@Valid @RequestBody  BillpaymentdateUnitamount clientpaymentdateRequest) {
			
			
				   Optional<BillpaymentdateUnitamount> billpaymentdateunitamountData = billpaymentdateunitamountRepository.findByClientid(clientpaymentdateRequest.getClientid());
					LocalDate date=clientpaymentdateRequest.getPaymentdate();   
				   if (billpaymentdateunitamountData.isPresent()) {
				    	  
				    	  
				    	  	
				    	  	
				    	 // 	SimpleDateFormat formatDateJava = new SimpleDateFormat("yyyy-mm-dd");
				    //	   String todayDateStr = formatDateJava.format(date);
				  
				 
				    	  BillpaymentdateUnitamount  _billpaymentdateunitamountData = billpaymentdateunitamountData.get();
						    	  _billpaymentdateunitamountData.setClientid(clientpaymentdateRequest.getClientid());
						    	 // _billpaymentdateunitamountData.setPaymentdate(LocalDate.parse(todayDateStr));
//						    	  _billpaymentdateunitamountData.setPaymentdate(LocalDate.parse("1993-11-23"));
						    	  _billpaymentdateunitamountData.setPaymentdate(date);
						    	  _billpaymentdateunitamountData.setAmountperunit(clientpaymentdateRequest.getAmountperunit());
						    	   

						    	  billpaymentdateunitamountRepository.save(_billpaymentdateunitamountData);	    	
							return ResponseEntity.ok(new SuccessResponse("101","Bill payment date updated  successfully!"));
				      }else {
				    	  
				    	  BillpaymentdateUnitamount clientpaymentdate = new BillpaymentdateUnitamount(
									clientpaymentdateRequest.getClientid(),
									date,
									clientpaymentdateRequest.getAmountperunit()
							
									);
				    	  billpaymentdateunitamountRepository.save(clientpaymentdate);

							return ResponseEntity.ok(new SuccessResponse("101","Bill payment date added  successfully!"));
				      }
				
				     

			}
		  
	
	  @PostMapping("/addclient")
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		public ResponseEntity<?> registerclient(@Valid @RequestBody ClientDetail clientRequest) {
			if (clientRepository.existsByUsername(clientRequest.getUsername())) {
				   throw new Exception("Error: Username already exists!");
			}
			if (clientRepository.existsByEmail(clientRequest.getEmail())) {
				   throw new Exception("Error: Email already exists!");
			}
			
			ClientDetail client = new ClientDetail(
					clientRequest.getFullname(),
					clientRequest.getUsername(),
					clientRequest.getPhonenumber(),
					clientRequest.getEmail(),
					clientRequest.getLocation(),
					clientRequest.getModule(),
					//clientRequest.getShortcode(),
					"Active",
					 encoder.encode(clientRequest.getPassword())
			
					);

			
			Set<Role> roles = new HashSet<>();
			Role adminRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(()-> new Exception("Error: Role is not found"));
			roles.add(adminRole);

			client.setRoles(roles);
			clientRepository.save(client);

			return ResponseEntity.ok(new SuccessResponse("101","Client registered successfully!"));
		}
	  
	  		
	  	@PostMapping("/addclientagents")
		@PreAuthorize("hasRole('ROLE_USER')")
		public ResponseEntity<?> registerclientagents(@Valid @RequestBody ConsumerAgent clientRequest) {
	  		if (!consumeragentRepository.existsById(clientRequest.getConsumerusername())) {
				return ResponseEntity
						.badRequest()
						.body(new ErrorResponse("201","Error: Client with that username was not found!.Login again or contact system provider."));
			}
	  		if (consumeragentRepository.existsByUsername(clientRequest.getUsername())) {
	  			return ResponseEntity
					.badRequest()
					.body(new ErrorResponse("201","Error: Agent Username already exists!."));
	
			}
	  		

			if (consumeragentRepository.existsByEmail(clientRequest.getEmail())) {
				return ResponseEntity.ok(new ErrorResponse("201","Error: Client with that username was not found!.Login again or contact system provider."));
				
			}
			
			
			
			ConsumerAgent client = new ConsumerAgent(
					clientRequest.getConsumerusername(),
					clientRequest.getFullname(),
					clientRequest.getUsername(),
					clientRequest.getPhonenumber(),
					clientRequest.getEmail(),
					"Active",
					"Water Agent",
					 encoder.encode(clientRequest.getPassword())
			
					);

			
			Set<Role> roles = new HashSet<>();
			Role adminRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
					.orElseThrow(()-> new Exception("Error: Role is not found"));
			roles.add(adminRole);

			client.setRoles(roles);
			consumeragentRepository.save(client);

			return ResponseEntity.ok(new SuccessResponse("101","Agent registered successfully!"));
		}
	  	
	  	
	  	
	  	@GetMapping("/getonewaterconsumeragent/{id}/{consumerid}")
		@PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<?> getonewaterconsumeragents(@PathVariable("id") String id,
				  @PathVariable("consumerid") String consumerid) {
		    try {
		    	Optional<ConsumerAgent> waterconsumeragentdetails = consumeragentRepository.findByIdAndConsumerusername(id,consumerid);

		  	    if (waterconsumeragentdetails.isPresent()) {
		  	      return new ResponseEntity<>(waterconsumeragentdetails.get(), HttpStatus.OK);

		  	    } else {
		  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Water consumer agent was not found!."));
					
		  	    }

		    } catch (Exception e) {
				   throw new Exception("Error: Consumer details  not found!");
				   }
		  }
	  	

		  @PutMapping("/updatewaterconsumeragent/{id}/{consumerid}")
			@PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<?> updatewaterconsumeragent(@PathVariable("id") String id,
				  @PathVariable("consumerid") String consumerid, @RequestBody  ConsumerAgent clientRequest) {


		    	Optional<ConsumerAgent> waterconsumeragentdetails = consumeragentRepository.findByIdAndConsumerusername(id,consumerid);

		  	    if (waterconsumeragentdetails.isPresent()) {
		  	    	ConsumerAgent _updateLoad = waterconsumeragentdetails.get();
		  	    	_updateLoad.setFullname(clientRequest.getFullname());
		  	    	_updateLoad.setUsername(clientRequest.getUsername());
			    	_updateLoad.setPhonenumber(clientRequest.getPhonenumber());
			    	_updateLoad.setEmail(clientRequest.getEmail());
			    	_updateLoad.setStatus(clientRequest.getStatus());
			    	_updateLoad.setAgenttype("Water Agent");
			   
    
    
    return new ResponseEntity<>(consumeragentRepository.save(_updateLoad), HttpStatus.OK);

		  	    } else {
		  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Water consumer Agent was not found!."));
					
		  	    }

		  }

		  @DeleteMapping("/deletewaterconsumeragent/{id}/{consumerid}")
			@PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<?> deletewaterconsumeragent(@PathVariable("id") String id,
				  @PathVariable("consumerid") String consumerid) {


		    	Optional<ConsumerAgent> waterconsumeragentdetails = consumeragentRepository.findByIdAndConsumerusername(id,consumerid);

		  	    if (waterconsumeragentdetails.isPresent()) {
		  	    	ConsumerAgent _agentLoad = waterconsumeragentdetails.get();
			    	String consumerusername=consumerid;
		  	    	String fullname= _agentLoad.getFullname();
		  	    	String username=_agentLoad.getUsername();
			    	String phonenumber=_agentLoad.getPhonenumber();
			    	String email=_agentLoad.getEmail();
			    	String status=_agentLoad.getStatus();
			    	String agenttype=_agentLoad.getAgenttype();
			    	String fromwhichtable="Consumeragent";
			    	String deletedby=consumerid;
			    	
			    	TrashTable client = new TrashTable(
			    			consumerusername,
			    			fullname,
			    			username,
			    			phonenumber,
							email,
						    status,
						    agenttype,
						    fromwhichtable,
						    deletedby
							);

					
					Set<Role> roles = new HashSet<>();
					Role adminRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(()-> new Exception("Error: Role is not found"));
					roles.add(adminRole);

					client.setRoles(roles);
					trashTableRepository.save(client);
			    	
			    	consumeragentRepository.deleteById(id);

					return ResponseEntity.ok(new SuccessResponse("101","Consumer Agent Deleted Successfully"));	
		  	    } else {
		  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Water consumer Agent was not found!."));
					
		  	    }

		  }
	  	
	  	@GetMapping("/allconsumeragents/{consumername}")
			@PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<List<ConsumerAgent>> getAllconsumeragentsclients(@PathVariable("consumername") String consumername) {
		    try {
		      List<ConsumerAgent> agentsdetails = new ArrayList<ConsumerAgent>();

		      consumeragentRepository.findAllByConsumerusernameAndAgenttype(consumername,"Water Agent").forEach(agentsdetails::add);
		      
		      return new ResponseEntity<>(agentsdetails, HttpStatus.CREATED);

		    } catch (Exception e) {
				   throw new Exception("Error: Agents details  not found!");}
		    
		  }
		
	  	@PostMapping("/addwaterconsumer")
		@PreAuthorize("hasRole('ROLE_USER')")
		public ResponseEntity<?> registeruserclient(@Valid @RequestBody WaterconsumerDetail clientRequest) {
			if (clientuserRepository.existsByMeternumber(clientRequest.getMeternumber())) {
				   throw new Exception("Error: Meter No. already exists!");
			}
		
			WaterconsumerDetail client = new WaterconsumerDetail(
					clientRequest.getClientid(),
					clientRequest.getFullname(),
					clientRequest.getLocation(),
					clientRequest.getPhonenumber(),
					clientRequest.getEmail(),
					clientRequest.getMeternumber(),
					"Active");

					clientuserRepository.save(client);

			return ResponseEntity.ok(new SuccessResponse("101","Consumer registered successfully!"));
		}
	  	
	  	@GetMapping("/allactivewaterconsumers/{clientid}")
			@PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<List<WaterconsumerDetail>> getAllactivesuserclients(@PathVariable("clientid") String clientid) {
		    try {
		      List<WaterconsumerDetail> clientdetails = new ArrayList<WaterconsumerDetail>();

		      clientuserRepository.findAllByStatusAndClientid("Active",clientid).forEach(clientdetails::add);
		      
		      return new ResponseEntity<>(clientdetails, HttpStatus.CREATED);

		    } catch (Exception e) {
				   throw new Exception("Error: Consumer details  not found!");}
		  }
	  	
	  	@GetMapping("/allinactivewaterconsumers/{clientid}")
		  @PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<List<WaterconsumerDetail>> getAllinactivesuserclients(@PathVariable("clientid") String clientid) {
		    try {
		      List<WaterconsumerDetail> clientdetails = new ArrayList<WaterconsumerDetail>();

		      clientuserRepository.findAllByStatusAndClientid("In-Active",clientid).forEach(clientdetails::add);
		      
		      return new ResponseEntity<>(clientdetails, HttpStatus.CREATED);

		    } catch (Exception e) {
				   throw new Exception("Error: Consumer details  not found!");}
		  }
	  	
	  		  	
	  	@GetMapping("/getonewaterconsumer/{id}/{consumerid}")
		@PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<?> getonewaterconsumers(@PathVariable("id") String id,
				  @PathVariable("consumerid") String consumerid) {
		    try {
		    	Optional<WaterconsumerDetail> waterconsumerdetails = clientuserRepository.findByIdAndClientid(id,consumerid);

		  	    if (waterconsumerdetails.isPresent()) {
		  	      return new ResponseEntity<>(waterconsumerdetails.get(), HttpStatus.OK);

		  	    } else {
		  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Water consumer was not found!."));
					
		  	    }

		    } catch (Exception e) {
				   throw new Exception("Error: Consumer details  not found!");
				   }
		  }

		  @PutMapping("/updatewaterconsumer/{id}/{consumerid}")
			@PreAuthorize("hasRole('ROLE_USER')")
		  public ResponseEntity<?> updatewaterconsumer(@PathVariable("id") String id,
				  @PathVariable("consumerid") String consumerid, @RequestBody WaterconsumerDetail waterconsumerdetail) {

		    	Optional<WaterconsumerDetail> waterconsumerdetails = clientuserRepository.findByIdAndClientid(id,consumerid);

		  	    if (waterconsumerdetails.isPresent()) {
		  	    	WaterconsumerDetail _updateLoad = waterconsumerdetails.get();
		  	    	_updateLoad.setFullname(waterconsumerdetail.getFullname());
		  	    	_updateLoad.setLocation(waterconsumerdetail.getLocation());
			    	_updateLoad.setPhonenumber(waterconsumerdetail.getPhonenumber());
			    	_updateLoad.setEmail(waterconsumerdetail.getEmail());
			    	_updateLoad.setMeternumber(waterconsumerdetail.getMeternumber());
			    	_updateLoad.setStatus(waterconsumerdetail.getStatus());
			
      
      
      return new ResponseEntity<>(clientuserRepository.save(_updateLoad), HttpStatus.OK);

		  	    } else {
		  	     // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		  	    	return ResponseEntity.ok(new ErrorResponse("201","Error: Water consumer was not found!."));
					
		  	    }

		  }
	  	
	  	
	  @GetMapping("/allactiveclients")
		@PreAuthorize("hasRole('ROLE_ADMIN')")
	  public ResponseEntity<List<ClientDetail>> getAllactivesclients(@RequestParam(required = false) String clientdetail) {
	    try {
	      List<ClientDetail> clientdetails = new ArrayList<ClientDetail>();
	      

			Role adminRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(()-> new Exception("Error: Role is not found"));

	      clientRepository.findAllByStatusAndRoles("Active",adminRole).forEach(clientdetails::add);
	      
	      return new ResponseEntity<>(clientdetails, HttpStatus.CREATED);

	    } catch (Exception e) {
			   throw new Exception("Error: Client details  not found!");}
	  }
	  
	  @GetMapping("/allinactiveclients")
		@PreAuthorize("hasRole('ROLE_ADMIN')")
	  public ResponseEntity<List<ClientDetail>> getAllinactiveclients(@RequestParam(required = false) String clientdetail) {
	    try {
	      List<ClientDetail> clientdetails = new ArrayList<ClientDetail>();

	      Role adminRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(()-> new Exception("Error: Role is not found"));

	      clientRepository.findAllByStatusAndRoles("In-Active",adminRole).forEach(clientdetails::add);
	      
	      
	      return new ResponseEntity<>(clientdetails, HttpStatus.CREATED);

	    } catch (Exception e) {
			   throw new Exception("Error: Client details  not found!");}
	  }
	  
	  
	
}
