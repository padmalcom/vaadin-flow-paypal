package de.stonedrum.vaadin.payment.paypal.payment.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerProtection {
	private String status;
	
	@JsonProperty("dispute_categories")
	private ArrayList<String> disputeCategories;
}
