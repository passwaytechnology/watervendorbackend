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
public class User {
	@Id
//	@JsonProperty("id")
	//@Column(name="id")//myql and sql
	private String id;
	
	@NotBlank
	@Size(max=50)
	@Email
	@JsonProperty("email")
	@Column(name="email")
	private String email;//mongodb
	
	@JsonProperty("fullname")
	@Column(name="fullname")
	private String fullname;
	

	@JsonProperty("username")
	@Column(name="username")
	private String username;
	
	
	@JsonProperty("phonenumber")
	@Column(name="phonenumber")
	private String phonenumber;
	
	@JsonProperty("status")
	@Column(name="status")
	private String status;
	
	@JsonProperty("password")
	@Column(name="password")
	private String password;
	
	@DBRef
	private Set<Role> roles=new HashSet<>();
	
	@JsonProperty("adminrole")
	@Column(name="adminrole")
	private String adminrole;
	
	@DBRef
	private Set<AdminRole> adminroledetails=new HashSet<>();


//	public User() {
//	
//	}
//	
//	
//	public User(String emailId, String userName, String password) {
//	//	super();
//		//this.id = id;
//		this.email = emailId;
//		this.username = userName;
//		this.password = password;
//	}
//	public String getId() { 
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
//	public String getEmailId() {
//		return email;
//	}
//	public void setEmailId(String email) {
//		this.email = email;
//	}
//	public String getUserName() {
//		return userName;
//	}
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
	
	
	
	public User() {
		
	  }

	  public User(String fullname,String username, String email, String password) {
	    this.fullname = fullname;
	    this.username = username;
	    this.email = email;
	    this.password = password;
	  }
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public String getAdminrole() {
		return adminrole;
	}

	public void setAdminrole(String adminrole) {
		this.adminrole = adminrole;
	}

	public Set<AdminRole> getAdminroledetails() {
		return adminroledetails;
	}

	public void setAdminroledetails(Set<AdminRole> adminroledetails) {
		this.adminroledetails = adminroledetails;
	}
	
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
