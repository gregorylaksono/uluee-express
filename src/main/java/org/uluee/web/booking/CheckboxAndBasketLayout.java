package org.uluee.web.booking;

import org.uluee.web.component.MainPage;
import org.uluee.web.component.window.CommodityTableLayout;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.themes.ValoTheme;

public class CheckboxAndBasketLayout extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5537467878187699205L;
	private OptionGroup bookingOption;
	private Button basketButton;

	public CheckboxAndBasketLayout() {
		createContents();
	}

	private void createContents() {
		setSpacing(true);
		bookingOption = new OptionGroup();
		bookingOption.addItem("Deprature");
		bookingOption.addItem("Arrival");

		basketButton = new Button("");
		basketButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		basketButton.addStyleName(ValoTheme.BUTTON_SMALL);
		basketButton.setIcon(FontAwesome.SHOPPING_CART);

		setWidth(100, Unit.PERCENTAGE);
		setHeight(40, Unit.PIXELS);
		addComponent(bookingOption);
		addComponent(basketButton);

		setExpandRatio(bookingOption, 1.0f);
		setExpandRatio(basketButton, 0.0f);

		setComponentAlignment(bookingOption, Alignment.MIDDLE_LEFT);
		setComponentAlignment(basketButton, Alignment.MIDDLE_RIGHT);

	}

	public OptionGroup getBookingOption() {
		return bookingOption;
	}

	public void setBookingOption(OptionGroup bookingOption) {
		this.bookingOption = bookingOption;
	}

	public Button getBasketButton() {
		return basketButton;
	}

	public void setBasketButton(Button basketButton) {
		this.basketButton = basketButton;
	}
	
	
}
