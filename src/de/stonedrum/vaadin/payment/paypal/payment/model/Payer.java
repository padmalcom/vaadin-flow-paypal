package de.stonedrum.vaadin.payment.paypal.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payer {
	private Name name;
	
	@JsonProperty("email_address")
	private String emailAddress;
	
	@JsonProperty("payer_id")
	private String payerId;
	
	private Address address;
}
