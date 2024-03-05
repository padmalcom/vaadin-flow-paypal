package de.stonedrum.vaadin.payment.paypal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Amount {
	@JsonProperty("currency_code")
	private String currencyCode;
	private String value;
}
