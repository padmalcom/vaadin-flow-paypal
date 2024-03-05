package de.stonedrum.vaadin.payment.paypal.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Name {
	@JsonProperty("full_name")
	private String fullName;
	
	@JsonProperty("given_name")
	private String givenName;
	
	private String surname;
}
