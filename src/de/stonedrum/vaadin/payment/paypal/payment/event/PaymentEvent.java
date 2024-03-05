package de.stonedrum.vaadin.payment.paypal.payment.event;

import com.vaadin.flow.component.ComponentEvent;

import de.stonedrum.vaadin.payment.paypal.payment.PaypalPayment;
import de.stonedrum.vaadin.payment.paypal.payment.model.OrderData;
import lombok.Getter;

@Getter
public class PaymentEvent extends ComponentEvent<PaypalPayment> {

	private static final long serialVersionUID = 1L;

	private OrderData orderData;
	
	public PaymentEvent(PaypalPayment source, boolean fromClient, OrderData orderData) {
		super(source, fromClient);
		this.orderData = orderData;
	}
}
