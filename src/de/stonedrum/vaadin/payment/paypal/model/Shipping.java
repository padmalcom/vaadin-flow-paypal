package de.stonedrum.vaadin.payment.paypal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shipping {
	private Name name;
	private Address address;
}
