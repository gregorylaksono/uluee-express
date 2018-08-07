package org.uluee.web.booking;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.component.LoginPage;
import org.uluee.web.component.MainPage;
import org.uluee.web.component.PreferencePage;
import org.uluee.web.component.window.CommodityTableLayout;
import org.uluee.web.util.NavigatorConstant;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.client.ui.Icon;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;

public class CheckboxAndBasketLayout extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5537467878187699205L;
	private Button basketButton;
	private Button loginButton;
//	private boolean isLoggedIn;
//	private ClickListener loginListener = new ClickListener() {
//		
//		@Override
//		public void buttonClick(ClickEvent event) {
//			loginButton.removeClickListener(logoutListener);
//			UIFactory.addWindow(new LoginPage(), false, false, true, true);
//		}
//	};
	
	private ClickListener logoutListener = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			Page.getCurrent()
            .setLocation(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());
         VaadinSession.getCurrent().close();
//			((Uluee_expressUI)UI.getCurrent()).getNavigator().navigateTo(NavigatorConstant.LOGIN_PAGE);
		}
	};

	public CheckboxAndBasketLayout() {
		createContents();
	}

	private void createContents() {
		setSpacing(true);
		basketButton = new Button("");
		basketButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		basketButton.addStyleName(ValoTheme.BUTTON_SMALL);
		basketButton.setIcon(FontAwesome.SHOPPING_CART);
		
		loginButton = new Button("Login");
		if(((Uluee_expressUI)UI.getCurrent()).isUserFlaggedLoged()) {
			loginButton.setCaption("Logout");
//			isLoggedIn = false;
			loginButton.addClickListener(logoutListener);
		}
		loginButton.setStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		loginButton.addStyleName(ValoTheme.BUTTON_SMALL);
		
		setHeight(40, Unit.PIXELS);
		MenuBar menuBar = getMenuButton("");
		addComponent(loginButton);
		addComponent(basketButton);
		addComponent(menuBar);

		setExpandRatio(basketButton, 0.0f);
		setExpandRatio(loginButton, 0.0f);
		setExpandRatio(menuBar, 0.0f);

		setComponentAlignment(basketButton, Alignment.MIDDLE_RIGHT);
		setComponentAlignment(loginButton, Alignment.MIDDLE_RIGHT);
		setComponentAlignment(menuBar, Alignment.MIDDLE_RIGHT);

	}
    private MenuBar getMenuButton(String caption) {
        MenuBar split = new MenuBar();
        MenuBar.MenuItem dropdown = split.addItem(caption, null);
        split.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        split.addStyleName(ValoTheme.MENUBAR_SMALL);
        dropdown.addItem("Logout", null);
        dropdown.addItem("Preferences", e->{
        	Window w = new Window();
        	w.setClosable(true);
        	w.setModal(true);
        	w.setResizable(false);
        	w.setContent(new PreferencePage());
        	w.setWidth(650, Unit.PIXELS);
        	UI.getCurrent().addWindow(w);
        });
        dropdown.addItem("Help", null);

        return split;
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
