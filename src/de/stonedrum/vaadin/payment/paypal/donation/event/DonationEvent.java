package de.stonedrum.vaadin.payment.paypal.donation.event;

import com.vaadin.flow.component.ComponentEvent;

import de.stonedrum.vaadin.payment.paypal.donation.PaypalDonation;
import de.stonedrum.vaadin.payment.paypal.payment.model.OrderData;
import lombok.Getter;

@Getter
public class DonationEvent extends ComponentEvent<PaypalDonation> {

	private static final long serialVersionUID = 1L;

	private OrderData orderData;
	
	public DonationEvent(PaypalDonation source, boolean fromClient, OrderData orderData) {
		super(source, fromClient);
		this.orderData = orderData;
	}
}
