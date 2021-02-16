package passway.technology.paaspay.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import passway.technology.paaspay.jwt.JwtUtils;
import passway.technology.paaspay.exception.Exception;
import passway.technology.paaspay.model.ERole;
import passway.technology.paaspay.model.Role;
import passway.technology.paaspay.model.User;
import passway.technology.paaspay.payload.JwtResponse;
import passway.technology.paaspay.payload.LoginRequest;
import passway.technology.paaspay.payload.SignupRequest;
import passway.technology.paaspay.payload.SuccessResponse;
import passway.technology.paaspay.repository.RoleRepository;
import passway.technology.paaspay.repository.UserRepository;
import passway.technology.paaspay.service.User_Details;





@CrossOrigin(origins = "http://watervendors.passwaytechnology.co.ke", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		   
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
					
		User_Details userDetails = (User_Details) authentication.getPrincipal();
			
						List<String> roles = userDetails.getAuthorities().stream()
								.map(item -> item.getAuthority())
								.collect(Collectors.toList());
				
						return ResponseEntity.ok(new JwtResponse(jwt, 
																 userDetails.getId(), 
																 userDetails.getFullname(),
																 userDetails.getUsername(), 
																 userDetails.getEmail(), 
																 roles));
	
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Username is already taken!"));
			   throw new Exception("Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//			return ResponseEntity
//					.badRequest()
//					.body(new MessageResponse("Error: Email is already in use!"));

			   throw new Exception("Error: Email is already taken!");
		}

		// Create new user's account
		User user = new User(signUpRequest.getFullname(), 
							 signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));


		Set<Role> roles = new HashSet<>();
		Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
				.orElseThrow(()-> new Exception("Error: Role is not found"));
		roles.add(adminRole);

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new SuccessResponse("101","User registered successfully!"));
	}
	
	
	
	
}
