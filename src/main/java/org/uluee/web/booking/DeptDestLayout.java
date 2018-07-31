package org.uluee.web.booking;

import java.util.List;
import java.util.Map;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;
import org.uluee.web.component.MainPage;
import org.uluee.web.component.window.GoogleMapNewDestLayout;
import org.uluee.web.util.Constant;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;

public class DeptDestLayout extends HorizontalLayout{
	
	private ComboBox deptCb ;
	private ComboBox destCb ;
	private DateField fromDate;



	private ValueChangeListener deptChangeListener = new ValueChangeListener() {
		
		@Override
		public void valueChange(ValueChangeEvent event) {
			String sessionId = ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
			String value = (String) event.getProperty().getValue();
			int lgth = value.length();
			value = new String(value.substring(lgth - 5, lgth - 2));
			Map<String, List<String>> airports = ((Uluee_expressUI) UI.getCurrent()).getWebServiceCaller().getAirportList(Constant.caIdForAirport, sessionId, value);
			List<String> to = airports.get("to");
			destCb.removeAllItems();
			for(String s: to){
				destCb.addItem(s);
			}
		}
	};

	public DeptDestLayout() {
		createContents();
	}

	private void createContents() {
		setSpacing(true);
		setWidth(100, Unit.PERCENTAGE);
		setHeight(70, Unit.PIXELS);
		deptCb = new ComboBox("From");
		destCb = new ComboBox("to");
		
		deptCb.setNullSelectionAllowed(false);
		destCb.setNullSelectionAllowed(false);
		
		deptCb.addValueChangeListener(deptChangeListener);
		deptCb.setImmediate(true);
		

		
		fromDate = new DateField("From");
		fromDate.setWidth(100, Unit.PERCENTAGE);
		fromDate.setResolution(Resolution.MINUTE);		
		
		addComponent(deptCb);
		addComponent(destCb);
		addComponent(fromDate);
		
	}


	public String getDept() {
		String objDept = String.valueOf(deptCb.getValue());
		String splitFrom = objDept.toString();
		int lgthFrom = splitFrom.length();
		splitFrom = new String(splitFrom.substring(lgthFrom - 5, lgthFrom - 2));
		return splitFrom;
	}


	public String getDest() {
		String objDept = String.valueOf(destCb.getValue());
		String splitFrom = objDept.toString();
		int lgthFrom = splitFrom.length();
		splitFrom = new String(splitFrom.substring(lgthFrom - 5, lgthFrom - 2));
		return splitFrom;
	}

	public ComboBox getDeptCb() {
		return deptCb;
	}

	public ComboBox getDestCb() {
		return destCb;
	}

	public DateField getFromDate() {
		return fromDate;
	}

	public void setFromDate(DateField fromDate) {
		this.fromDate = fromDate;
	}


	
	

}
