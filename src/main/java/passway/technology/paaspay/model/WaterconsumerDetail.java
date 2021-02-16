package passway.technology.paaspay.model;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="waterconsumers")
public class WaterconsumerDetail {
	@Id
	private String id;
	
	@JsonProperty("clientid")
	@Column(name="clientid")
	private String clientid;

	@JsonProperty("fullname")
	@Column(name="fullname")
	private String fullname;
	

	@JsonProperty("location")
	@Column(name="location")
	private String location;
	

	@JsonProperty("phonenumber")
	@Column(name="phonenumber")
	private String phonenumber;
	
	@NotBlank
	@Size(max=50)
	@Email
	@JsonProperty("email")
	@Column(name="email")
	private String email;//mongodb
	
	
	@JsonProperty("meternumber")
	@Column(name="meternumber")
	private String meternumber;
	

	@JsonProperty("status")
	@Column(name="status")
	private String status;
	

	public WaterconsumerDetail() {
		
	  }

	public WaterconsumerDetail(String clientid,String fullname, String location, String phonenumber, String email, String meternumber,String status) {
 
	  this.clientid = clientid;
	  this.fullname = fullname;
	  this.location = location;
	  this.phonenumber = phonenumber;
	  this.email = email;
	  this.meternumber = meternumber;
	  this.status = status;

	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		id = id;
	}
	

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getMeternumber() {
		return meternumber;
	}

	public void setMeternumber(String meternumber) {
		this.meternumber = meternumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
	
}
