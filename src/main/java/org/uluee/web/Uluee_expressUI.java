package org.uluee.web;

import java.util.Map;

import javax.servlet.annotation.WebServlet;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.uluee.web.cloud.IWebService;
import org.uluee.web.cloud.WebServiceCaller;
import org.uluee.web.cloud.model.BookingConfirmation;
import org.uluee.web.cloud.model.User;
import org.uluee.web.component.BookingPage;
import org.uluee.web.component.ConfirmPage;
import org.uluee.web.component.DummyPage;
import org.uluee.web.component.MainPage;
import org.uluee.web.util.Constant;
import org.uluee.web.util.NavigatorConstant;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.server.Page.UriFragmentChangedListener;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("uluee_express")
@StyleSheet("https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css")
@PreserveOnRefresh
public class Uluee_expressUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = Uluee_expressUI.class)
	public static class Servlet extends VaadinServlet {
	}
	
	 static {
		    SLF4JBridgeHandler.install();
		  }

	private CssLayout content;
	private Navigator navigator;
	private IWebService webServiceCaller = new WebServiceCaller();
	private User user = null;

	private String sessionKey;
	private BookingConfirmation confirmation;
	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout parent_root = createParentRoot();
		setContent(parent_root);
		
		navigator = new Navigator(this, content);
//		navigator.addView(NavigatorConstant.PAYPAL_PAGE, DummyPage.class);
//		navigator.addView(NavigatorConstant.BOOKING_PAGE, BookingPage.class);
		navigator.addView(NavigatorConstant.MAIN_PAGE, MainPage.class);
		navigator.addView(NavigatorConstant.CONFIRM_PAGE, ConfirmPage.class);
		
		user = webServiceCaller.login(Constant.USERNAME, Constant.PASSWORD);
		
		Map requestParam = request.getParameterMap();
		String[] confirm = (String[]) requestParam.get("v-loc");
		String param  = getParam(confirm);
		if(param == null) {
			navigator.navigateTo(NavigatorConstant.MAIN_PAGE);			
		}else if(param.contains("paymentId") && param.contains("token")){
			navigator.navigateTo(NavigatorConstant.CONFIRM_PAGE+"/"+param);
		}else{
			navigator.navigateTo(NavigatorConstant.MAIN_PAGE);
		}
	}

	private String getParam(String[] confirm) {
		String param = null;
		if((confirm != null) && 
		   (confirm.length > 0) && 
		   (confirm[0].contains("paymentId") && 
				   confirm[0].contains("token") && 
				   confirm[0].contains(NavigatorConstant.CONFIRM_PAGE))) {
			
			String text = confirm[0];			
			int index = text.indexOf("paymentId");
			param = text.substring(index, text.length());
		}
		return param;
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

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
		
	}
	
	public String getSessionKey() {
		return this.sessionKey;
	}

	public void resetLayer(AbstractOrderedLayout layer) {
		content.removeAllComponents();
		content.addComponent(layer);
	}

	public void setBookingData(BookingConfirmation confirmation) {
		 this.confirmation = confirmation;
		
	}
	
	public BookingConfirmation getBookingData(){
		return confirmation;
	}
	


}