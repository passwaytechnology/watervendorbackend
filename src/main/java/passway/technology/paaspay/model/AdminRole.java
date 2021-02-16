package passway.technology.paaspay.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="adminroles")
public class AdminRole {
	@Id
	private String id;
	
	@JsonProperty("name")
	@Column(name="name")
	private String name;

	public AdminRole() {
		
	  }

	  public AdminRole(String name) {
	    this.name = name;
	   
	  }
	  
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	  
}
