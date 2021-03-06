package org.uluee.web.booking;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.component.MainPage;
import org.uluee.web.component.window.CommodityTableLayout;
import org.uluee.web.component.window.LoginComponent;
import org.uluee.web.util.NavigatorConstant;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class CheckboxAndBasketLayout extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5537467878187699205L;
	private OptionGroup bookingOption;
	private Button basketButton;
	private Button loginButton;
//	private boolean isLoggedIn;
	private ClickListener loginListener = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			loginButton.removeClickListener(logoutListener);
			UIFactory.addWindow(new LoginComponent(), false, false, true, true);
		}
	};
	
	private ClickListener logoutListener = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			loginButton.removeClickListener(loginListener);
			((Uluee_expressUI)UI.getCurrent()).setUserLoggedFlaged(false);
			((Uluee_expressUI)UI.getCurrent()).setUserLabel("");
			((Uluee_expressUI)UI.getCurrent()).login();
			((Uluee_expressUI)UI.getCurrent()).getNavigator().navigateTo(NavigatorConstant.MAIN_PAGE);
		}
	};

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
		
		loginButton = new Button("Login");
		if(((Uluee_expressUI)UI.getCurrent()).isUserFlaggedLoged()) {
			loginButton.setCaption("Logout");
//			isLoggedIn = false;
			loginButton.addClickListener(logoutListener);
		}else {
			loginButton.addClickListener(loginListener);
			
		}
		loginButton.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		loginButton.addStyleName(ValoTheme.BUTTON_SMALL);
		
		setWidth(100, Unit.PERCENTAGE);
		setHeight(40, Unit.PIXELS);
		addComponent(bookingOption);
		addComponent(loginButton);
		addComponent(basketButton);

		setExpandRatio(bookingOption, 1.0f);
		setExpandRatio(basketButton, 0.0f);
		setExpandRatio(loginButton, 0.0f);

		setComponentAlignment(bookingOption, Alignment.MIDDLE_LEFT);
		setComponentAlignment(basketButton, Alignment.MIDDLE_RIGHT);
		setComponentAlignment(loginButton, Alignment.MIDDLE_RIGHT);

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

	public Button getLoginButton() {
		return loginButton;
	}

	public void setLoginButton(Button loginButton) {
		this.loginButton = loginButton;
	}
	
	
}
