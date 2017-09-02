package org.uluee.web.component.window;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.BookingComponent;
import org.uluee.web.cloud.model.FlightSchedule;
import org.uluee.web.component.BookingPage;
import org.uluee.web.util.NavigatorConstant;
import org.uluee.web.util.UIFactory;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class DisclaimerLayout extends CustomLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean initValue = false;
	private BookingComponent bookingComponent;
	public DisclaimerLayout(BookingComponent bookingComponent) {
		super("Disclaimer");
		this.bookingComponent = bookingComponent;
		addStyleName("auto-height");
		createContents();
	}

	private void createContents() {
		CheckBox agreeCheckBox = new CheckBox("");
		agreeCheckBox.setImmediate(true);
		final Button submitButton = new Button("Submit");
		submitButton.addStyleName(ValoTheme.BUTTON_SMALL);
		submitButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		addComponent(agreeCheckBox, "checkBox");
		addComponent(submitButton, "submitButton");
		agreeCheckBox.addValueChangeListener(e ->{
			Boolean isChecked = (Boolean) e.getProperty().getValue();
			submitButton.setEnabled(isChecked);
		});
		agreeCheckBox.setValue(initValue);
		submitButton.setEnabled(initValue);
		submitButton.addClickListener(e ->{
			LinkedHashMap m = bookingComponent.getParam();
//			((Uluee_expressUI)UI.getCurrent()).getNavigator().navigateTo(NavigatorConstant.BOOKING_PAGE+
//					"/"+bookingComponent.getStringCommodities()+"/"+m.get("shipperName")+
//					"/"+m.get("consigneeName")+"/"+m.get("minDep")+"/"+m.get("maxArr")+"/"+m.get("shipperAddId")+
//					"/"+m.get("latitudeShipper")+"/"+m.get("longitudeShipper")+"/"+m.get("consigneeAddId")+
//					"/"+m.get("latitudeConsignee")+"/"+m.get("longitudeConsignee"));
//			UIFactory.closeAllWindow();
			((Uluee_expressUI)UI.getCurrent()).resetLayer(new BookingPage(m));
			
		});
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
