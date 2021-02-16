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
public class ConsumerAgent {
	@Id
	private String id;
	

	@JsonProperty("fullname")
	@Column(name="fullname")
	private String fullname;
	

	@JsonProperty("username")
	@Column(name="username")
	private String username;
	
	@JsonProperty("consumerusername")
	@Column(name="consumerusername")
	private String consumerusername;
	
	@JsonProperty("phonenumber")
	@Column(name="phonenumber")
	private String phonenumber;
	
	@NotBlank
	@Size(max=50)
	@Email
	@JsonProperty("email")
	@Column(name="email")
	private String email;//mongodb
	
	@JsonProperty("status")
	@Column(name="status")
	private String status;
	
	
	@JsonProperty("agenttype")
	@Column(name="agenttype")
	private String agenttype;


	@JsonProperty("password")
	@Column(name="password")
	private String password;
	

	@DBRef
	private Set<Role> roles=new HashSet<>();
	
		public ConsumerAgent() {
			
		  }

	  public ConsumerAgent(String consumerusername, String fullname, String username,
			  String phonenumber, String email,String status,String agenttype,String password) {
	    this.consumerusername = consumerusername;
	    this.fullname = fullname;
	    this.username = username;
	    this.phonenumber = phonenumber;
	    this.email = email;
	    this.status = status;
	    this.agenttype = agenttype;
	    this.password = password;
	  }
	  
		public String getConsumerusername() {
			return consumerusername;
		}

		public void setConsumerusername(String consumerusername) {
			this.consumerusername = consumerusername;
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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		
		public String getAgenttype() {
			return agenttype;
		}

		public void setAgenttype(String agenttype) {
			this.agenttype = agenttype;
		}

		public Set<Role> getRoles() {
			return roles;
		}

		public void setRoles(Set<Role> roles) {
			this.roles = roles;
		}

	  
	  
}
