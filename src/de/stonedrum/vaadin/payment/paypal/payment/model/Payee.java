package de.stonedrum.vaadin.payment.paypal.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Payee {
	@JsonProperty("email_address")
	private String emailAddress;

	@JsonProperty("merchant_id")
	private String merchantId;
}
