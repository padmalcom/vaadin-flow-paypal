package de.stonedrum.vaadin.payment.paypal.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.shared.Registration;

import de.stonedrum.vaadin.payment.paypal.payment.event.PaymentEvent;
import de.stonedrum.vaadin.payment.paypal.payment.model.OrderData;
import elemental.json.JsonObject;

@Tag("paypal-payment")
public class PaypalPayment extends Div {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(PaypalPayment.class);

	public static final String LAYOUT = "vertical";
	public static final String COLOR = "gold";
	public static final String SHAPE = "rect";
	public static final String LABEL = "label";
	public static final String SUCCESS_MESSAGE = "<h3>Thank you for your payment!</h3>";

	public PaypalPayment(String paypalClientId, float paymentAmount, String paymentCurrency, String sdkCurrency) {
		this(paypalClientId, paymentAmount, paymentCurrency, sdkCurrency, LAYOUT, COLOR, SHAPE, LABEL, SUCCESS_MESSAGE);
	}
	
	public PaypalPayment(String paypalClientId, float paymentAmount, String paymentCurrency, String sdkCurrency, String layout,
			String color, String shape, String label, String successMessage) {
		this.setId("paypal-button-container");

		UI.getCurrent().getPage()
				.addJavaScript(String.format("https://www.paypal.com/sdk/js?client-id=%s&currency=%s", paypalClientId, sdkCurrency));

		Div paypalButtonContainer = new Div();
		paypalButtonContainer.setId("button-container");

		this.add(paypalButtonContainer);
		this.getElement().executeJs(String.format("""
				paypal.Buttons({
					style: {
						layout: '%s',
						color:  '%s',
						shape:  '%s',
						label:  '%s'
					},
					createOrder: function(data, actions) {
						return actions.order.create({
				        	purchase_units: [{"amount":{"currency_code": "%s", "value": %.2f}}]
				        });
					},


				    onApprove: function(data, actions) {
				    	return actions.order.capture().then(function(orderData) {
							document.getElementById('paypal-button-container').$server.approve(orderData);

				            const element = document.getElementById('paypal-button-container');
				            element.innerHTML = '';
				            element.innerHTML = '%s';

				            // Or go to another URL:  actions.redirect('thank_you.html');

				        });
				    },

				    onError: function(err) {
				    	document.getElementById('paypal-button-container').$server.error(err);
				    }
				}).render('#button-container');
				""", layout, color, shape, label, paymentCurrency, paymentAmount, successMessage));
	}

	public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
			ComponentEventListener<T> listener) {
		return getEventBus().addListener(eventType, listener);
	}

	@ClientCallable
	private void approve(final JsonObject data) {
		logger.debug("Received approve payment: {}", data.toJson());

		ObjectMapper om = new ObjectMapper();
		try {
			OrderData od = om.readValue(data.toJson(), OrderData.class);

			// https://levelup.gitconnected.com/how-to-use-custom-events-in-vaadin-6c4a187088c
			fireEvent(new PaymentEvent(this, false, od));
		} catch (Exception e) {
			logger.error("Error while parsing payment response.", e);
		}
	}

	@ClientCallable
	private void error(final JsonObject err) {
		logger.debug("Received error during payment: {}", err.toJson());
	}

}
