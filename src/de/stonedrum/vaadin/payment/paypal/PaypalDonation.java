package de.stonedrum.vaadin.payment.paypal;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;

@JavaScript("https://www.paypalobjects.com/donate/sdk/donate-sdk.js")
@Tag("paypal-donation")
public class PaypalDonation extends Div {

	private static final long serialVersionUID = 1L;
	
	public PaypalDonation(String id) {
		this.setId("donate-button-container");
		
		Div donateButton = new Div();
		donateButton.setId("donate-button");

		this.add(donateButton);
		this.getElement().executeJs(String.format("""
				
		PayPal.Donation.Button({
			env:'production',
			hosted_button_id:'%s',
			image: {
				src:'https://www.paypalobjects.com/de_DE/DE/i/btn/btn_donate_LG.gif',
				alt:'Spenden mit dem PayPal-Button',
				title:'PayPal - The safer, easier way to pay online!',
			}
		}).render('#donate-button');
		""", id));
	}
}
