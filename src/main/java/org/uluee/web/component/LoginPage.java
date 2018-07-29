package org.uluee.web.component;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.User;
import org.uluee.web.util.NavigatorConstant;
import org.uluee.web.util.UIFactory;

import com.paypal.api.payments.Notification;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

public class LoginPage extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4243650330064587462L;

	public LoginPage() {
		setSpacing(true);
		setMargin(true);
		createContent();
	}

	private void createContent() {
		FormLayout form = new FormLayout();
		final TextField username_text = new TextField("Username: ");
		final PasswordField password_text = new PasswordField("Password:");

		form.setWidth(null);
		form.addComponent(username_text);
		form.addComponent(password_text);

		Button login_button = new Button("Login");
		login_button.setStyleName(ValoTheme.BUTTON_PRIMARY);
		login_button.addStyleName(ValoTheme.BUTTON_SMALL);

		HorizontalLayout login_layout = new HorizontalLayout();
		login_layout.addComponent(login_button);
		login_layout.setWidth(100, Unit.PERCENTAGE);
		login_layout.setHeight(null);
		login_layout.setComponentAlignment(login_button,Alignment.MIDDLE_RIGHT);

		addComponent(form);
		addComponent(login_layout);

		setExpandRatio(form, 1.0f);
		setExpandRatio(login_layout, 0.0f);

		login_button.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				String username = username_text.getValue();
				String password = password_text.getValue();
				if(username.equals("") || password.equals("")) {
					com.vaadin.ui.Notification.show("Login data can not be empty", Type.ERROR_MESSAGE);
					return;
				}
				User user = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().login(username, password);
				if(user == null) {
					com.vaadin.ui.Notification.show("Authentication is failed", Type.ERROR_MESSAGE);
					return;
				}
				login_button.setCaption("Please wait...");
				login_button.setEnabled(false);
				getUI().access(new Runnable() {

					@Override
					public void run() {
						((Uluee_expressUI)UI.getCurrent()).setUser(user);
						((Uluee_expressUI)UI.getCurrent()).setUserLoggedFlaged(true);
						((Uluee_expressUI)UI.getCurrent()).setUserLabel("Welcome "+user.getUsername());
						((Uluee_expressUI)UI.getCurrent()).getNavigator().navigateTo(NavigatorConstant.MAIN_PAGE);	
					}
				});
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}



}
