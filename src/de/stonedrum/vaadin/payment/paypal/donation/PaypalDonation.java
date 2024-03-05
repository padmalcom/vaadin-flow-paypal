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
import de.stonedrum.vaadin.payment.paypal.donation.model.DonationData;
import de.stonedrum.vaadin.payment.paypal.donation.model.DonationEnvironment;
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
	
	private static final String IMG_SRC = "https://www.paypalobjects.com/de_DE/DE/i/btn/btn_donate_LG.gif";
	private static final String IMG_ALT = "Spenden mit dem PayPal-Button";
	private static final String IMG_TITLE = "PayPal - The safer, easier way to pay online!";
	
	public PaypalDonation(String id, DonationEnvironment env, String imgSrc, String imgAlt, String imgTitle) {
		this.setId("donate-button-container");
		
		Div donateButton = new Div();
		donateButton.setId("donate-button");

		this.add(donateButton);
		this.getElement().executeJs(String.format("""
				
		PayPal.Donation.Button({
			env:'%s',
			hosted_button_id:'%s',
			image: {
				src:'%s',
				alt:'%s',
				title:'%s',
			},
			onComplete: function (params) {
				document.getElementById('donate-button-container').$server.donationComplete(params);
			}
		}).render('#donate-button');
		""", env.toString(), id, imgSrc, imgAlt, imgTitle));		
	}
	
	public PaypalDonation(String id, DonationEnvironment env) {
		this(id, env, IMG_SRC, IMG_ALT, IMG_TITLE);
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
			DonationData dd = om.readValue(data.toJson(), DonationData.class);

			// https://levelup.gitconnected.com/how-to-use-custom-events-in-vaadin-6c4a187088c
			fireEvent(new DonationEvent(this, false, dd));
		} catch (Exception e) {
			logger.error("Error while parsing donation response.", e);
		}
	}
}
