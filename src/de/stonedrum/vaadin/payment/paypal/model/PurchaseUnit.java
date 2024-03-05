package de.stonedrum.vaadin.payment.paypal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PurchaseUnit {
	
	@JsonProperty("reference_id")
	private String referenceId;
	private Amount amount;
	private Payee payee;
	private Shipping shipping;
	private Payments payments;
}
