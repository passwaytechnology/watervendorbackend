package passway.technology.paaspay.model;

import java.time.LocalDate;

import javax.persistence.Column;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection="mpesatransactions")
public class MpesaTransaction {
	@Id
	private String id;
	

	@JsonProperty("systemlocaldatime")
	@Column(name="systemlocaldatime")
	private LocalDate systemlocaldatime;
	
	@JsonProperty("clientid")
	@Column(name="clientid")
	private String clientid;
	

	@JsonProperty("transamount")
	@Column(name="transamount")
	private String transamount;
	

	@JsonProperty("depositor")
	@Column(name="depositor")
	private String depositor;
	

	@JsonProperty("transactiontype")
	@Column(name="transactiontype")
	private String transactiontype;
	

	@JsonProperty("transid")
	@Column(name="transid")
	private String transid;
	
	@JsonProperty("msisdn")
	@Column(name="msisdn")
	private String msisdn;
	
	@JsonProperty("transtime")
	@Column(name="transtime")
	private LocalDate transtime;
	
	@JsonProperty("billrefnumber")
	@Column(name="billrefnumber")
	private String billrefnumber;
	
	@JsonProperty("orgaccountbalance")
	@Column(name="orgaccountbalance")
	private String orgaccountbalance;

	@JsonProperty("approvedby")
	@Column(name="approvedby")
	private String approvedby;
	
	@JsonProperty("approvaldate")
	@Column(name="approvaldate")
	private LocalDate approvaldate;
	
	@JsonProperty("status")
	@Column(name="status")
	private String status;

	public MpesaTransaction() {
		
	  }

	public MpesaTransaction(LocalDate systemlocaldatime,String clientid, String transamount, 
			String depositor,String transactiontype,
			String transid,String msisdn, LocalDate transtime, String billrefnumber,
			String orgaccountbalance,String approvedby,LocalDate approvaldate,String status) {
		 
		  this.systemlocaldatime = systemlocaldatime;
		  this.clientid = clientid;
		  this.transamount = transamount;
		  this.depositor = depositor;
		  this.transactiontype = transactiontype;
		  this.transid = transid;
		  this.msisdn = msisdn;
		  this.transtime = transtime;
		  this.billrefnumber = billrefnumber;
		  this.orgaccountbalance = orgaccountbalance;
		  this.approvedby = approvedby;
		  this.approvaldate = approvaldate;
		  this.status = status;

		}
	
	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		id = id;
	}


	public LocalDate getSystemlocaldatime() {
		return systemlocaldatime;
	}


	public void setSystemlocaldatime(LocalDate systemlocaldatime) {
		this.systemlocaldatime = systemlocaldatime;
	}


	public String getClientname() {
		return clientid;
	}


	public void setClientname(String clientid) {
		this.clientid = clientid;
	}


	public String getTransamount() {
		return transamount;
	}


	public void setTransamount(String transamount) {
		transamount = transamount;
	}


	public String getDepositor() {
		return depositor;
	}


	public void setDepositor(String depositor) {
		depositor = depositor;
	}


	public String getTransactiontype() {
		return transactiontype;
	}


	public void setTransactiontype(String transactiontype) {
		transactiontype = transactiontype;
	}


	public String getTransid() {
		return transid;
	}


	public void setTransid(String transid) {
		transid = transid;
	}


	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		msisdn = msisdn;
	}


	public LocalDate getTranstime() {
		return transtime;
	}


	public void setTranstime(LocalDate transtime) {
		transtime = transtime;
	}


	public String getBillrefnumber() {
		return billrefnumber;
	}


	public void setBillrefnumber(String billrefnumber) {
		billrefnumber = billrefnumber;
	}


	public String getOrgaccountbalance() {
		return orgaccountbalance;
	}


	public void setOrgaccountbalance(String orgaccountbalance) {
		orgaccountbalance = orgaccountbalance;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getApprovedby() {
		return approvedby;
	}


	public void setApprovedby(String approvedby) {
		this.approvedby = approvedby;
	}


	public LocalDate getApprovaldate() {
		return approvaldate;
	}


	public void setApprovaldate(LocalDate approvaldate) {
		this.approvaldate = approvaldate;
	}
	
	

	
}
