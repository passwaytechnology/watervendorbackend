package passway.technology.paaspay.model;

import java.time.LocalDate;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.type.Date;

@Document(collection="billpaymentdateunitamounts")
public class BillpaymentdateUnitamount {

	@Id
	private String id;
	

	@JsonProperty("clientid")
	@Column(name="clientid")
	private String clientid;
	
	//@JsonFormat(pattern="mm/dd/yyyy")
	//@JsonFormat(pattern="yyyy-mm-dd")
//	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone="GMT")
	@JsonProperty("paymentdate")
	private LocalDate   paymentdate;

	@JsonProperty("amountperunit")
	@Column(name="amountperunit")
	private String amountperunit;
	

	public BillpaymentdateUnitamount() {
		
	  }
	
	public BillpaymentdateUnitamount(String clientid, LocalDate paymentdate,String amountperunit) {
		 
		  this.clientid = clientid;
		  this.paymentdate = paymentdate;
		  this.amountperunit = amountperunit;


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

	public LocalDate getPaymentdate() {
		return paymentdate;
	}

	public void setPaymentdate(LocalDate localDate) {
		this.paymentdate = localDate;
	}

	public String getAmountperunit() {
		return amountperunit;
	}

	public void setAmountperunit(String amountperunit) {
		this.amountperunit = amountperunit;
	}
	
	
}
