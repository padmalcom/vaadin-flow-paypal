package de.stonedrum.vaadin.payment.paypal.donation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;

import de.stonedrum.vaadin.payment.paypal.donation.event.DonationEvent;
import de.stonedrum.vaadin.payment.paypal.donation.model.DonationEnvironment;
import de.stonedrum.vaadin.payment.paypal.payment.model.OrderData;
import elemental.json.JsonObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JavaScript("https://www.paypalobjects.com/donate/sdk/donate-sdk.js")
@Tag("paypal-donation")
public class PaypalDonation extends Div {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(PaypalDonation.class);
	
	public PaypalDonation(String id, DonationEnvironment env) {
		this.setId("donate-button-container");
		
		Div donateButton = new Div();
		donateButton.setId("donate-button");

		this.add(donateButton);
		this.getElement().executeJs(String.format("""
				
		PayPal.Donation.Button({
			env:'%s',
			hosted_button_id:'%s',
			image: {
				src:'https://www.paypalobjects.com/de_DE/DE/i/btn/btn_donate_LG.gif',
				alt:'Spenden mit dem PayPal-Button',
				title:'PayPal - The safer, easier way to pay online!',
			},
			onComplete: function (params) {

			}
		}).render('#donate-button');
		""", env.toString(), id));
	}
	
	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}
	
	@ClientCallable
	private void donationComplete(final JsonObject data) {
		logger.debug("Donation completed: {}", data.toJson());

		ObjectMapper om = new ObjectMapper();
		try {
			OrderData od = om.readValue(data.toJson(), OrderData.class);

			// https://levelup.gitconnected.com/how-to-use-custom-events-in-vaadin-6c4a187088c
			fireEvent(new DonationEvent(this, false, od));
		} catch (Exception e) {
			logger.error("Error while parsing donation response.", e);
		}
	}
}
