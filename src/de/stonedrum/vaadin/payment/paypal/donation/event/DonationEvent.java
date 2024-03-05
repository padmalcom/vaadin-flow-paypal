package de.stonedrum.vaadin.payment.paypal.donation.event;

import com.vaadin.flow.component.ComponentEvent;

import de.stonedrum.vaadin.payment.paypal.donation.PaypalDonation;
import de.stonedrum.vaadin.payment.paypal.donation.model.DonationData;
import lombok.Getter;

@Getter
public class DonationEvent extends ComponentEvent<PaypalDonation> {

	private static final long serialVersionUID = 1L;

	private DonationData donationData;
	
	public DonationEvent(PaypalDonation source, boolean fromClient, DonationData donationData) {
		super(source, fromClient);
		this.donationData = donationData;
	}
}
