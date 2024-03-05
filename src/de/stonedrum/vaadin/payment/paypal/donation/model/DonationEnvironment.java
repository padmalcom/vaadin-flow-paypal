package de.stonedrum.vaadin.payment.paypal.donation.model;

public enum DonationEnvironment {

	SANDBOX("sandbox"), PRODUCTION("production");

	private final String name;

	private DonationEnvironment(String s) {
		name = s;
	}

	public String toString() {
		return this.name;
	}
}
