package passway.technology.paaspay.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import passway.technology.paaspay.model.User;
import passway.technology.paaspay.repository.RegistrationRepository;

@Service
public class RegistrationService {
	@Autowired
	private RegistrationRepository repo;
	 
	public User saveUser(User user) {
		return repo.save(user);
		
	}
}
