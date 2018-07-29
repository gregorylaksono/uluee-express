package org.uluee.web.component.window;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.BookingComponent;
import org.uluee.web.cloud.model.FlightSchedule;
import org.uluee.web.component.BookingPage;
import org.uluee.web.util.Constant;
import org.uluee.web.util.NavigatorConstant;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.Util;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class DisclaimerWindow extends Window{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean initValue = false;
	private BookingComponent bookingComponent;
	private CustomLayout parent = new CustomLayout("Disclaimer");
	public DisclaimerWindow(BookingComponent bookingComponent) {
		this.bookingComponent = bookingComponent;
		this.parent.addStyleName("auto-height");
		setClosable(true);
		setModal(true);
		setDraggable(false);
		setResizable(false);
		center();
		
		Panel p = new Panel();
		p.setContent(parent);
		p.setHeight(500, Unit.PIXELS);
		setContent(p);
		createContents();
	}

	private void createContents() {
		CheckBox agreeCheckBox = new CheckBox("");
		agreeCheckBox.setImmediate(true);
		final Button submitButton = new Button("Submit");
		submitButton.addStyleName(ValoTheme.BUTTON_SMALL);
		submitButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		this.parent.addComponent(agreeCheckBox, "checkBox");
		this.parent.addComponent(submitButton, "submitButton");
		agreeCheckBox.addValueChangeListener(e ->{
			Boolean isChecked = (Boolean) e.getProperty().getValue();
			submitButton.setEnabled(isChecked);
		});
		agreeCheckBox.setValue(initValue);
		submitButton.setEnabled(initValue);
		submitButton.addClickListener(e ->{
			submitButton.setEnabled(false);
			LinkedHashMap m = bookingComponent.getParam();
			String commodities = buildCommodities((List<String>)m.get("commodities"));
//			this.consigneeId = msgs[0];
//			this.consigneeType = msgs[1];
//			this.shipperId = msgs[2];
//			this.consigneeParentId = msgs[3];
//			this.ffwId = msgs[4];
//			
//			String dep = Util.CONVERT_DATE_FORMAT.format(Util.NORMAL_DATE_FORMAT.parse(msgs[5]));
//			String arr = Util.CONVERT_DATE_FORMAT.format(Util.NORMAL_DATE_FORMAT.parse(msgs[6]));
//			this.commodities  = msgs[7];
//			this.deprature = msgs[8];
//			this.destination = msgs[9];
		
			
			((Uluee_expressUI)UI.getCurrent()).getNavigator().navigateTo(NavigatorConstant.BOOKING_PAGE+"/"
					+m.get("consignee")+"/"
					+m.get("shipperId")+"/"
					+m.get("agentId")+"/"
					+m.get("minDeparture")+"/"
					+m.get("maxArrival")+"/"
					+commodities+"/"
					+m.get("deprature")+"/"
					+m.get("destination"));
					
			DisclaimerWindow.this.close();
//			((Uluee_expressUI)UI.getCurrent()).resetLayer(new BookingPage(m));
			
		});
	}

	private String buildCommodities(List<String> list) {
		String result = "";
		for(String s: list){
			result = s+"&&"+result;
		}
		result = result.substring(0, result.length()-2);
		return result;
	}

	public boolean isInitValue() {
		return initValue;
	}

	public void setInitValue(boolean initValue) {
		this.initValue = initValue;
	}

	public BookingComponent getBookingComponent() {
		return bookingComponent;
	}

	public void setBookingComponent(BookingComponent bookingComponent) {
		this.bookingComponent = bookingComponent;
	}

}
