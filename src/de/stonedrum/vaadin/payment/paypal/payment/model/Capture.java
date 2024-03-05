package de.stonedrum.vaadin.payment.paypal.payment.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Capture {
	private String id;
	
	private String status;
	
	private Amount amount;
	
	@JsonProperty("final_capture")
	private boolean finalCapture;
	
	@JsonProperty("seller_protection")
	private SellerProtection sellerProtection;
	
	@JsonProperty("create_time")
	private Date createTime;
	
	@JsonProperty("update_time")
	private Date updateTime;
}
