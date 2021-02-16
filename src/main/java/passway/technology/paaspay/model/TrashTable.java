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

@Document(collection="trashtable")
public class TrashTable {
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
	

	@JsonProperty("fromwhichtable")
	@Column(name="fromwhichtable")
	private String fromwhichtable;


	@JsonProperty("deletedby")
	@Column(name="deletedby")
	private String deletedby;


	@DBRef
	private Set<Role> roles=new HashSet<>();

	public TrashTable() {
		
	  }

	public TrashTable(String consumerusername, String fullname, String username,
			  String phonenumber, String email,String status,String agenttype, String fromwhichtable,
			  String deletedby) {
	  this.consumerusername = consumerusername;
	  this.fullname = fullname;
	  this.username = username;
	  this.phonenumber = phonenumber;
	  this.email = email;
	  this.status = status;
	  this.agenttype = agenttype;
	  this.fromwhichtable = fromwhichtable;
	  this.deletedby = deletedby;
	}
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public String getConsumerusername() {
		return consumerusername;
	}


	public void setConsumerusername(String consumerusername) {
		this.consumerusername = consumerusername;
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


	public String getAgenttype() {
		return agenttype;
	}


	public void setAgenttype(String agenttype) {
		this.agenttype = agenttype;
	}
	

	public String getFromwhichtable() {
		return fromwhichtable;
	}


	public void setFromwhichtable(String fromwhichtable) {
		this.fromwhichtable = fromwhichtable;
	}


	public String getDeletedby() {
		return deletedby;
	}


	public void setDeletedby(String deletedby) {
		this.deletedby = deletedby;
	}


	public Set<Role> getRoles() {
		return roles;
	}


	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	

	
	
	
}
