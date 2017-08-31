package org.uluee.web.component;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.BookingConfirmation;
import org.uluee.web.cloud.model.DataPaymentTempDTD;
import org.uluee.web.util.NavigatorConstant;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class ConfirmPage extends VerticalLayout implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5866362853099308229L;
	private DataPaymentTempDTD tempData = null;
	@Override
	public void enter(ViewChangeEvent event) {
		String param = event.getParameters();
		String tokenId = "";
		String paymentId = "";
		if(param != null && !param.equals("")) {
			String[] paramSplit = param.split("&");
			for(String p : paramSplit) {
				int endIndex = p.indexOf("=");
				String key = p.substring(0, endIndex);
				String value = p.substring(endIndex+1, p.length());
				if(key.equalsIgnoreCase("paymentId")) {
					paymentId = value;
				}else if(key.equalsIgnoreCase("token")) {
					tokenId = value;
				}
			}
			
			tempData = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getTempData(tokenId, paymentId);
			createContents();
		}else {
			((Uluee_expressUI)UI.getCurrent()).getNavigator().navigateTo(NavigatorConstant.MAIN_PAGE);
		}
		
	}
	
	public ConfirmPage() {
		UI.getCurrent().getPage().setTitle("Confirm payment");
		setMargin(true);
		setSpacing(true);
		setHeight(100, Unit.PERCENTAGE);
		setWidth(100, Unit.PERCENTAGE);
	}

	private void createContents() {
		Label caption = new Label("<span style=\"text-align:center;display:inline-block;"
				+ " width:100%\"><h2>Please confirm the below transaction</h2></span>");
		caption.setHeight(null);
		caption.setWidth(100, Unit.PERCENTAGE);
		caption.setContentMode(ContentMode.HTML);
		
		HorizontalLayout layout = new HorizontalLayout();
		layout.setWidth(100, Unit.PERCENTAGE);
		layout.setHeight(100, Unit.PIXELS);
		layout.setSpacing(true);
		
		Label text = new Label();
		text.setValue("<span style=\"text-align:right; display:inline-block; width:100%"
				+ "\">Total cost: <b>"+tempData.getAmount_from()+" "+tempData.getCurrency_from()+"</b></span>");
		text.setContentMode(ContentMode.HTML);
		
		Button b = new Button("Pay");
		b.setWidth(null);
		b.setStyleName(ValoTheme.BUTTON_SMALL);
		b.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		b.addClickListener(e ->{
			String sessionKey = ((Uluee_expressUI)UI.getCurrent()).getSessionKey();
			
			BookingConfirmation confirmation = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().createBookingDoorToDoorNew(sessionKey, tempData.getRateId());
			if(confirmation != null) {
				
			}
		});
		
		layout.addComponent(text);
		layout.addComponent(b);
		
		layout.setComponentAlignment(text, Alignment.MIDDLE_RIGHT);
		layout.setComponentAlignment(b, Alignment.MIDDLE_LEFT);
		
		
		addComponent(caption);
		addComponent(layout);
		setExpandRatio(caption, 0.0f);
		setExpandRatio(layout, 1.0f);
		setComponentAlignment(caption, Alignment.TOP_CENTER);
		setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
	}

}
