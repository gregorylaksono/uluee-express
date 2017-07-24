package org.uluee.web.booking;

import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.ButtonSize;
import org.uluee.web.util.UIFactory.ButtonStyle;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TracingTab extends VerticalLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -49662624116770105L;
	private static final String REMARK = "remark";
	private static final String DATE = "date";
	private static final String STATUS = "Status";
	private Label tPiecesL;
	private Label tweightL;
	private Label shipperL;
	private Label consigneeL;

	public TracingTab(String caption) {
		setCaption(caption);
		createContents();
		setSpacing(true);
		setMargin(true);
	}
	
	private void createContents() {
		HorizontalLayout awbLayout = createAwbLayout();
		Table lastStatus = createTableLastStatus();
		FormLayout summary = createSummaryLayout();
		HorizontalLayout buttonLayout = createButtonLayout();
		
		addComponent(awbLayout);
		addComponent(lastStatus);
		addComponent(summary);
		addComponent(buttonLayout);
		
		setExpandRatio(awbLayout, 0.0f);
		setExpandRatio(buttonLayout, 0.0f);
		setExpandRatio(summary, 0.0f);
		setExpandRatio(lastStatus, 1.0f);
		
		setComponentAlignment(buttonLayout, Alignment.BOTTOM_RIGHT);
	}
	
	private HorizontalLayout createButtonLayout() {
		HorizontalLayout parent = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.UNDEFINED, null, true);
		parent.setHeight(100, Unit.PERCENTAGE);
		parent.setWidth(50, Unit.PERCENTAGE);
		
		Button button1 = UIFactory.createButton(ButtonSize.SMALL, ButtonStyle.BORDERLESS,"Action 1");
		Button button2 = UIFactory.createButton(ButtonSize.SMALL, ButtonStyle.BORDERLESS,"Action 2");
		Button button3 = UIFactory.createButton(ButtonSize.SMALL, ButtonStyle.BORDERLESS,"Action 3");
		
		parent.addComponent(button1);
		parent.addComponent(button2);
		parent.addComponent(button3);
		
		parent.setComponentAlignment(button1, Alignment.MIDDLE_RIGHT);
		parent.setComponentAlignment(button2, Alignment.MIDDLE_RIGHT);
		parent.setComponentAlignment(button3, Alignment.MIDDLE_RIGHT);
		
		return parent;
	}

	private Table createTableLastStatus() {
		Table t = new Table("Status");
		t.setId("booking-table");
		
		t.addContainerProperty(STATUS, String.class, null);
		t.addContainerProperty(DATE, String.class, null);
		t.addContainerProperty(REMARK, String.class, null);
				
		t.setWidth(100, Unit.PERCENTAGE);
		t.setHeight(100, Unit.PIXELS);
		
		t.setColumnHeader(STATUS, "Status");
		t.setColumnHeader(DATE, "Date");
		t.setColumnHeader(REMARK, "Remark");
		
		return t;
	}

	private FormLayout createSummaryLayout() {
		FormLayout parent = new FormLayout();
		parent.setWidth(100, Unit.PERCENTAGE);
		parent.setHeight(130, Unit.PIXELS);
		parent.addStyleName("summary-form-layout");
		
		tPiecesL = new Label("");
		tweightL = new Label("");
		shipperL = new Label("");
		consigneeL = new Label("");
		
		tPiecesL.setCaption("Total pieces");
		tweightL.setCaption("Total Weight");
		shipperL.setCaption("Shipper");
		consigneeL.setCaption("Consignee");
		
		tPiecesL.setValue("1 pieces");
		tweightL.setValue("1.0 kg");
		shipperL.setValue("");
		consigneeL.setValue("");
		
		parent.addComponent(tPiecesL);
		parent.addComponent(tweightL);
		parent.addComponent(shipperL);
		parent.addComponent(consigneeL);
		return parent;
	}
	private HorizontalLayout createAwbLayout() {
		HorizontalLayout root = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, false);
		root.setHeight(70, Unit.PIXELS);
		root.setCaption("AWB No");
//		Label awbNoLabel = new Label("AWB No");
//		awbNoLabel.addStyleName(ValoTheme.LABEL_H2);
//		awbNoLabel.setWidth(null);
		
		CssLayout parent = new CssLayout();
		parent.setId("awb-search-section");
		parent.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		
		TextField awb1 = new TextField();
		TextField awb2 = new TextField();
		TextField awb3 = new TextField();
		
		awb1.setWidth(56, Unit.PIXELS);
		awb2.setWidth(63, Unit.PIXELS);
		awb3.setWidth(63, Unit.PIXELS);
		
		Button search = UIFactory.createButton(ButtonSize.NORMAL, ButtonStyle.PRIMARY, "Search");
		parent.addComponent(awb1);
		parent.addComponent(awb2);
		parent.addComponent(awb3);
		parent.addComponent(search);
		
//		root.addComponent(awbNoLabel);
		root.addComponent(parent);
		root.setComponentAlignment(parent, Alignment.MIDDLE_CENTER);
//		root.setExpandRatio(awbNoLabel, 0.0f);
		root.setExpandRatio(parent, 1.0f);
		
		return root;
	}


}
