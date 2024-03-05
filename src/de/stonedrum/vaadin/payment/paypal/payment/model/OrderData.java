package de.stonedrum.vaadin.payment.paypal.payment.model;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderData {

	private String id;
	private String intent;
	private String status;

	@JsonProperty("purchase_units")
	private ArrayList<PurchaseUnit> purchaseUnits;
	private Payer payer;

	@JsonProperty("create_time")
	private Date createTime;

	@JsonProperty("update_time")
	private Date updateTime;
	private ArrayList<Link> links;
}
