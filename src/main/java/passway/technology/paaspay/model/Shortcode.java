package passway.technology.paaspay.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="shortcodes")
public class Shortcode {
	
	@Id
	private String id;
	
	@JsonProperty("shortcode")
	@Column(name="shortcode")
	private String shortcode;
	

	@JsonProperty("client")
	@Column(name="client")
	private String client;
	
	


	@DBRef
	private Set<ClientDetail> clientdetail=new HashSet<>();



	public Shortcode() {
		
	  }

	  public Shortcode(String shortcode) {
	    this.shortcode = shortcode;
	   
	  }
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		id = id;
	}

	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}
	
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
	
	public Set<ClientDetail> getClientdetail() {
		return clientdetail;
	}

	public void setClientdetail(Set<ClientDetail> clientdetail) {
		this.clientdetail = clientdetail;
	}
	
	
}
