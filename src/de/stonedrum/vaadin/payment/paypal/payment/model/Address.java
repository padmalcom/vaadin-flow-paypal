package de.stonedrum.vaadin.payment.paypal.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {
	
	@JsonProperty("address_line_1")
	private String addressLine;
	
	@JsonProperty("admin_area_2")
	private String adminArea;
	
	@JsonProperty("postal_code")
	private String postalCode;
	
	@JsonProperty("country_code")
	private String countryCode;
}
