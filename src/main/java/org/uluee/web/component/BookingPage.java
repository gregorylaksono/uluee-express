package org.uluee.web.component;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class BookingPage extends VerticalLayout implements View{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7189514912734415277L;
	private static final String SELECT = "select";
	private static final String RATE = "rate";
	private static final String ARR_TIME = "arrTime";
	private static final String DEP_TIME = "depTime";
	private static final String TO = "to";
	private static final String FROM = "from";
	private Label tVolWeightL;
	private Label tvolumeL;
	private Label tweightL;
	private Label tPiecesL;

	@Override
	public void enter(ViewChangeEvent event) {
		
		
	}
	
	public BookingPage() {
		setMargin(true);
		setSpacing(true);
		setHeight(100, Unit.PERCENTAGE);
		createContents();
	}

	private void createContents() {
		Table table = createTable();
		FormLayout summary = createSummaryLayout();
		Button backButton = createBackButton();
		
		addComponent(table);
		addComponent(summary);
		addComponent(backButton);
		
		setExpandRatio(table, 1.0f);
		setExpandRatio(summary, 0.0f);
		setExpandRatio(backButton, 0.0f);
		
		setComponentAlignment(summary, Alignment.MIDDLE_LEFT);
		setComponentAlignment(backButton, Alignment.MIDDLE_LEFT);
	}

	private Button createBackButton() {
		Button button = new Button("Back");
		button.setStyleName(ValoTheme.BUTTON_SMALL);
		button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		return button;
	}

	private FormLayout createSummaryLayout() {
		FormLayout parent = new FormLayout();
		parent.setWidth(100, Unit.PERCENTAGE);
		parent.addStyleName("summary-form-layout");
		
		tPiecesL = new Label("");
		tweightL = new Label("");
		tvolumeL = new Label("");
		tVolWeightL = new Label("");
		
		tPiecesL.setCaption("Total pieces");
		tweightL.setCaption("Total Weight");
		tvolumeL.setCaption("Total Volume");
		tVolWeightL.setCaption("Volume/Weight");
		
		tPiecesL.setValue("1 pieces");
		tweightL.setValue("1.0 kg");
		tvolumeL.setValue("1 m3");
		tVolWeightL.setValue("0.17 kg");
		
		parent.addComponent(tPiecesL);
		parent.addComponent(tweightL);
		parent.addComponent(tvolumeL);
		parent.addComponent(tVolWeightL);
		return parent;
	}

	private Table createTable() {
		Table table = new Table();
		table.addStyleName("uluee-table");
		table.addContainerProperty(FROM, String.class, null);
		table.addContainerProperty(TO, String.class, null);
		table.addContainerProperty(DEP_TIME, String.class, null);
		table.addContainerProperty(ARR_TIME, String.class, null);
		table.addContainerProperty(RATE, String.class, null);
		table.addContainerProperty(SELECT, Button.class, null);
		
		table.setWidth(100, Unit.PERCENTAGE);
		table.setHeight(100, Unit.PERCENTAGE);
		
		table.setColumnHeader(FROM, "From");
		table.setColumnHeader(TO, "To");
		table.setColumnHeader(DEP_TIME, "Deprature time");
		table.setColumnHeader(ARR_TIME, "Arrival time");
		table.setColumnHeader(RATE, "Rate");
		table.setColumnHeader(SELECT, "");
		
		Item i = table.addItem("test");
		Button b = new Button("Select");
		b.setStyleName(ValoTheme.BUTTON_SMALL);
		b.addStyleName(ValoTheme.BUTTON_QUIET);
		
		i.getItemProperty(FROM).setValue("London");
		i.getItemProperty(TO).setValue("Berlin");
		i.getItemProperty(DEP_TIME).setValue("21/07/17 17:00");
		i.getItemProperty(ARR_TIME).setValue("21/07/17 19:00");
		i.getItemProperty(RATE).setValue("200 EUR");
		i.getItemProperty(SELECT).setValue(b);
		return table;
	}

}
