package de.stonedrum.vaadin.payment.paypal.donation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DonationData {
	@JsonProperty("tx")
	private String transactionId;
	
	@JsonProperty("st")
	private String status;
	
	@JsonProperty("amt")
	private float amount;
	
	@JsonProperty("cc")
	private String currencyCode;
	
	@JsonProperty("cm")
	private String customMessage;
	
	@JsonProperty("item_number")
	private String itemNumber;
	
	@JsonProperty("item_name")
	private String itemName;
}
