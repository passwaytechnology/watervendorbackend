package passway.technology.paaspay.model;

import java.time.LocalDate;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="waterbills")
public class WaterBill {
	@Id
	private String id;
	

	@JsonProperty("clientid")
	@Column(name="clientid")
	private String clientid;

	@JsonProperty("consumerid")
	@Column(name="consumerid")
	private String consumerid;
	

	@JsonProperty("meternumber")
	private String meternumber;
	

	@JsonProperty("invoicegenerationdate")
	private LocalDate   invoicegenerationdate;
	
	@JsonProperty("paymentdate")
	private LocalDate   paymentdate;
	

	@JsonProperty("previous_reading")
	private String   previous_reading;
	

	@JsonProperty("current_reading")
	private String   current_reading;
	
	@JsonProperty("noofunits")
	private String   noofunits;

	@JsonProperty("totalunitamount")
	private String totalunitamount;
	
	@JsonProperty("totalamountpaid")
	private String totalamountpaid;

	@JsonProperty("totalamountbalance")
	private String totalamountbalance;
	

	@JsonProperty("status")
	private String status;
	
	@JsonProperty("creationdate")
	private LocalDate creationdate;
	
	@JsonProperty("addedby")
	private String addedby;

	@JsonProperty("paidby")
	private String paidby;
	 // private String ClientDetail authorities;
	
	@JsonProperty("mpesacode")
	private String mpesacode;
	
	@JsonProperty("paymentmethod")
	private String paymentmethod;

	@JsonProperty("cashamount")
	private String cashamount;

	@JsonProperty("bankref")
	private String bankref;
	


	public WaterBill() {
		
	  }
	  
	public WaterBill(String clientid,String consumerid, String meternumber, 
			LocalDate invoicegenerationdate,LocalDate paymentdate,String previous_reading,String current_reading,
			String noofunits,String totalunitamount, String totalamountpaid, String totalamountbalance,
			String status,LocalDate creationdate,String addedby,String paidby) {
		 
		  this.clientid = clientid;
		  this.consumerid = consumerid;
		  this.meternumber = meternumber;
		  this.invoicegenerationdate = invoicegenerationdate;
		  this.paymentdate = paymentdate;
		  this.previous_reading = previous_reading;
		  this.current_reading = current_reading;
		  this.noofunits = noofunits;
		  this.totalunitamount = totalunitamount;
		  this.totalamountpaid = totalamountpaid;
		  this.totalamountbalance = totalamountbalance;
		  this.status = status;
		  this.creationdate = creationdate;
		  this.addedby = addedby;
		  this.paidby = paidby;


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


	public String getConsumerid() {
		return consumerid;
	}


	public void setConsumerid(String consumerid) {
		this.consumerid = consumerid;
	}
	
	
	public String getMeternumber() {
		return meternumber;
	}

	public void setMeternumber(String meternumber) {
		this.meternumber = meternumber;
	}



	public LocalDate getInvoicegenerationdate() {
		return invoicegenerationdate;
	}


	public void setInvoicegenerationdate(LocalDate invoicegenerationdate) {
		this.invoicegenerationdate = invoicegenerationdate;
	}


	public LocalDate getPaymentdate() {
		return paymentdate;
	}


	public void setPaymentdate(LocalDate paymentdate) {
		this.paymentdate = paymentdate;
	}

	public String getPrevious_reading() {
		return previous_reading;
	}

	public void setPrevious_reading(String previous_reading) {
		this.previous_reading = previous_reading;
	}

	public String getCurrent_reading() {
		return current_reading;
	}

	public void setCurrent_reading(String current_reading) {
		this.current_reading = current_reading;
	}


	public String getNoofunits() {
		return noofunits;
	}


	public void setNoofunits(String noofunits) {
		this.noofunits = noofunits;
	}


	public String getTotalunitamount() {
		return totalunitamount;
	}


	public void setTotalunitamount(String totalunitamount) {
		this.totalunitamount = totalunitamount;
	}


	public String getTotalamountpaid() {
		return totalamountpaid;
	}


	public void setTotalamountpaid(String totalamountpaid) {
		this.totalamountpaid = totalamountpaid;
	}


	public String getTotalamountbalance() {
		return totalamountbalance;
	}


	public void setTotalamountbalance(String totalamountbalance) {
		this.totalamountbalance = totalamountbalance;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	

	public LocalDate getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(LocalDate creationdate) {
		this.creationdate = creationdate;
	}

	public String getAddedby() {
		return addedby;
	}

	public void setAddedby(String addedby) {
		this.addedby = addedby;
	}

	public String getPaidby() {
		return paidby;
	}

	public void setPaidby(String paidby) {
		this.paidby = paidby;
	}
	
	public String getMpesacode() {
		return mpesacode;
	}

	public void setMpesacode(String mpesacode) {
		this.mpesacode = mpesacode;
	}

	public String getPaymentmethod() {
		return paymentmethod;
	}

	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}
	
	public String getCashamount() {
		return cashamount;
	}

	public void setCashamount(String cashamount) {
		this.cashamount = cashamount;
	}

	public String getBankref() {
		return bankref;
	}

	public void setBankref(String bankref) {
		this.bankref = bankref;
	}
	
	
}
