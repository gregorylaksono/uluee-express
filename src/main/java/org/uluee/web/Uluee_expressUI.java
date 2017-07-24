package org.uluee.web;

import javax.servlet.annotation.WebServlet;

import org.uluee.web.cloud.IWebService;
import org.uluee.web.cloud.WebServiceCaller;
import org.uluee.web.cloud.model.User;
import org.uluee.web.component.BookingPage;
import org.uluee.web.component.MainPage;
import org.uluee.web.util.Constant;
import org.uluee.web.util.NavigatorConstant;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("uluee_express")
public class Uluee_expressUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Uluee_expressUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private CssLayout content;
	private Navigator navigator;
	private IWebService webServiceCaller = new WebServiceCaller();
	private User user = null;
	
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout parent_root = createParentRoot();
		setContent(parent_root);
		
		navigator = new Navigator(this, content);
		navigator.addView(NavigatorConstant.MAIN_PAGE, MainPage.class);
		navigator.addView(NavigatorConstant.BOOKING_PAGE, BookingPage.class);
		navigator.navigateTo(NavigatorConstant.MAIN_PAGE);
		user = webServiceCaller.login(Constant.USERNAME, Constant.PASSWORD);
	}

	private VerticalLayout createParentRoot() {
		VerticalLayout layout = new VerticalLayout();
		layout.setId(CSSStyle.ROOT_PARENT);
		content = new CssLayout();
		content.setId(CSSStyle.ROOT_LAYOUT);
		
		layout.setSizeFull();

		layout.addComponent(content);
		layout.setComponentAlignment(content, Alignment.MIDDLE_CENTER);
		return layout;
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}

	public IWebService getWebServiceCaller() {
		return webServiceCaller;
	}

	public void setWebServiceCaller(IWebService webServiceCaller) {
		this.webServiceCaller = webServiceCaller;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	



}