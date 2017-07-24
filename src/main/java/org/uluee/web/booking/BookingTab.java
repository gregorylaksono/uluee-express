package org.uluee.web.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.uluee.web.Uluee_expressUI;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;

import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;


public class BookingTab extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5710971296555257916L;
	private OptionGroup option = new OptionGroup( "" ); 


	public BookingTab(){
		setCaption("Booking");
		createContents();
		setMargin(true);
		setSpacing(true);
	}

	private void createContents() {
		HorizontalLayout topLayout = createTopLayout();
		HorizontalLayout dateLayout = createDateLayout();
		Component fromField = createAutoCompleteComponent();
		Component toField = createAutoCompleteComponent();
		HorizontalLayout propertyLayout = createPropertyLayout();
		
		addComponent(topLayout);
		addComponent(dateLayout);
		addComponent(fromField);
		addComponent(toField);
		addComponent(propertyLayout);
		
		setExpandRatio(topLayout, 0.0f);
		setExpandRatio(dateLayout, 0.0f);
		setExpandRatio(fromField, 0.0f);
		setExpandRatio(toField, 0.0f);
		setExpandRatio(propertyLayout, 1.0f);
	}

	private HorizontalLayout createPropertyLayout() {
		HorizontalLayout parent = new HorizontalLayout();
		VerticalLayout leftLayout = new VerticalLayout();
		VerticalLayout rightLayout = new VerticalLayout();
		leftLayout.setMargin(true);
		rightLayout.setMargin(true);
		leftLayout.setWidth(100, Unit.PERCENTAGE);
		rightLayout.setWidth(100, Unit.PERCENTAGE);
		parent.addComponent(leftLayout);
		parent.addComponent(rightLayout);
		
		parent.setExpandRatio(leftLayout, 0.5f);
		parent.setExpandRatio(rightLayout, 0.5f);
		parent.setMargin(true);
		return parent;
	}

	private Component createAutoCompleteComponent() {
		AutocompleteField<String> field = new AutocompleteField<>();
		field.setWidth(100, Unit.PERCENTAGE);
		field.setQueryListener(new AutocompleteQueryListener<String>() {
			
			@Override
			public void handleUserQuery(AutocompleteField<String> arg0, String arg1) {
				System.out.println(arg1);
				
			}
		});
		return field;
	}


	private HorizontalLayout createDateLayout() {
		HorizontalLayout parent = new HorizontalLayout();
		DateField fromDateField = new DateField();
		DateField toDateField = new DateField();
		Label dummyLabel = new Label("");
		dummyLabel.setWidth(30, Unit.PIXELS);
		
		fromDateField.setWidth(100, Unit.PERCENTAGE);
		toDateField.setWidth(100, Unit.PERCENTAGE);
		parent.addComponent(fromDateField);
		parent.addComponent(dummyLabel);
		parent.addComponent(toDateField);
		parent.setExpandRatio(fromDateField, 0.5f);
		parent.setExpandRatio(dummyLabel, 0.0f);
		parent.setExpandRatio(toDateField, 0.5f);
		parent.setWidth(100, Unit.PERCENTAGE);
		return parent;
	}

	private HorizontalLayout createTopLayout() {
		HorizontalLayout boxLayout = createCheckBoxLayout();
		Button basketButton = createBasketButton();
		HorizontalLayout parent = new HorizontalLayout();
		parent.addComponent(boxLayout);
//		parent.addComponent(basketButton);
		parent.setExpandRatio(boxLayout, 1.0f);
//		parent.setExpandRatio(basketButton, 0.0f);
		
		return parent;
	}

	private Button createBasketButton() {
		// TODO Auto-generated method stub
		return null;
	}

	private HorizontalLayout createCheckBoxLayout() {
		HorizontalLayout parent = new HorizontalLayout();
		parent.setWidth(100, Unit.PERCENTAGE);
		parent.addComponent(option);
		option.addItem("Deprature");
		option.addItem("Arrival");
		option.setWidth(100, Unit.PERCENTAGE);
		return parent;
	}

}
