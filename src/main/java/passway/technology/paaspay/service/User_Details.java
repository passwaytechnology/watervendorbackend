package passway.technology.paaspay.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import passway.technology.paaspay.model.User;



public class User_Details implements UserDetails{
	private static final long serialVersionUID = 1L;

	private String id;

	private String fullname;
	
	private String username;


	private String email;   

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public User_Details(String id,String fullname, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static User_Details build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new User_Details(
				user.getId(),  
				user.getFullname(), 
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
	
	public String getFullname() {
		return fullname;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User_Details user = (User_Details) o;
		return Objects.equals(id, user.id);
	}

}
