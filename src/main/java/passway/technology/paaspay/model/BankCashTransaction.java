package passway.technology.paaspay.model;

import java.time.LocalDate;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="bankcashtransactions")
public class BankCashTransaction {
	@Id
	private String id;
	
	@JsonProperty("clientid")
	@Column(name="clientid")
	private String clientid;

	@JsonProperty("waterbillid")
	@Column(name="waterbillid")
	private String waterbillid;
	

	@JsonProperty("transactiontype")
	@Column(name="transactiontype")
	private String transactiontype;

	@JsonProperty("amount")
	@Column(name="amount")
	private String amount;
	

	@JsonProperty("bankref")
	@Column(name="bankref")
	private String bankref;
	
	
	@JsonProperty("status")
	@Column(name="status")
	private String status;
	
	
	@JsonProperty("addedby")
	@Column(name="addedby")
	private String addedby;
	
	
	@JsonProperty("updatedate")
	@Column(name="updatedate")
	private LocalDate updatedate;


	public BankCashTransaction() {
		
	  }

	public BankCashTransaction(String clientid,String waterbillid,String transactiontype,
			String amount, String bankref,
			String status, String addedby,LocalDate updatedate) {
 
	  this.clientid = clientid;
	  this.waterbillid = waterbillid;
	  this.transactiontype = transactiontype;
	  this.amount = amount;
	  this.bankref = bankref;
	  this.status = status;
	  this.addedby = addedby;
	  this.updatedate = updatedate;

	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getClientid() {
		return clientid;
	}


	public void setClientid(String clientid) {
		this.clientid = clientid;
	}


	public String getWaterbillid() {
		return waterbillid;
	}


	public void setWaterbillid(String waterbillid) {
		this.waterbillid = waterbillid;
	}
	
	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}


	public String getAmount() {
		return amount;
	}


	public void setAmount(String amount) {
		this.amount = amount;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBankref() {
		return bankref;
	}

	public void setBankref(String bankref) {
		this.bankref = bankref;
	}

	public String getAddedby() {
		return addedby;
	}

	public void setAddedby(String addedby) {
		this.addedby = addedby;
	}
	
	
	public LocalDate getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(LocalDate updatedate) {
		this.updatedate = updatedate;
	}
	
	
}
