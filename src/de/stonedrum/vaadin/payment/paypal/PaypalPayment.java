package de.stonedrum.vaadin.payment.paypal;

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

import de.stonedrum.vaadin.payment.paypal.event.PaymentEvent;
import de.stonedrum.vaadin.payment.paypal.model.OrderData;
import elemental.json.JsonObject;

@Tag("paypal-payment")
public class PaypalPayment extends Div {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = LoggerFactory.getLogger(PaypalPayment.class);

	public PaypalPayment(String paypalClientId) {
		this.setId("paypal-button-container");

		UI.getCurrent().getPage()
				.addJavaScript("https://www.paypal.com/sdk/js?client-id=" + paypalClientId + "&currency=EUR");

		Div paypalButtonContainer = new Div();
		paypalButtonContainer.setId("button-container");

		this.add(paypalButtonContainer);
		this.getElement().executeJs(String.format("""
				paypal.Buttons({
					style: {
						layout: 'vertical',
						color:  'gold',
						shape:  'rect',
						label:  'paypal'
					},
					createOrder: function(data, actions) {
						return actions.order.create({
				                  purchase_units: [{"amount":{"currency_code": "EUR", "value": 2.00}}]
				              });
					},


				          onApprove: function(data, actions) {
				              return actions.order.capture().then(function(orderData) {

				                  // Full available details
				                  console.log('Capture result', orderData, JSON.stringify(orderData, null, 2));

							document.getElementById('paypal-button-container').$server.approve(orderData);

				                  // Show a success message within this page, for example:
				                  const element = document.getElementById('paypal-button-container');
				                  element.innerHTML = '';
				                  element.innerHTML = '<h3>Thank you for your payment!</h3>';

				                  // Or go to another URL:  actions.redirect('thank_you.html');

				              });
				          },

				          onError: function(err) {
				              console.log(err);
				              document.getElementById('paypal-button-container').$server.error(err);
				          }
				}).render('#button-container');
				""", ""));
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
