package passway.technology.paaspay.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;




@Document(collection="users")
public class ClientDetail {
	@Id
	private String id;
	

	@JsonProperty("fullname")
	@Column(name="fullname")
	private String fullname;
	

	@JsonProperty("username")
	@Column(name="username")
	private String username;
	

	@JsonProperty("phonenumber")
	@Column(name="phonenumber")
	private String phonenumber;
	
	@NotBlank
	@Size(max=50)
	@Email
	@JsonProperty("email")
	@Column(name="email")
	private String email;//mongodb
	
	
	@JsonProperty("location")
	@Column(name="location")
	private String location;
	
	@JsonProperty("module")
	@Column(name="module")
	private String module;
	

	@JsonProperty("shortcode")
	@Column(name="shortcode")
	private String shortcode;
	
	@JsonProperty("status")
	@Column(name="status")
	private String status;
	
	@JsonProperty("password")
	@Column(name="password")
	private String password;
	

	@DBRef
	private Set<Role> roles=new HashSet<>();
	
	
		public ClientDetail() {
			
		  }

	  public ClientDetail(String fullname, String username, String phonenumber, String email, String location, 
			  String module,String status,String password) {
	    this.fullname = fullname;
	    this.username = username;
	    this.phonenumber = phonenumber;
	    this.email = email;
	    this.location = location;
	    this.module = module;
	 //   this.shortcode = shortcode;
	    this.status = status;
	    this.password = password;
	  }
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getStatus() {
		return status;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	

	
	
}
