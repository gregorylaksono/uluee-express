package org.uluee.web.booking;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.BookingConfirmation;
import org.uluee.web.cloud.model.CommodityItem;
import org.uluee.web.cloud.model.Status;
import org.uluee.web.util.CallSOAPAction;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.ButtonSize;
import org.uluee.web.util.UIFactory.ButtonStyle;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
	private Table statusTable;
	private TextField awb1;
	private TextField awb2;
	private TextField awb3;
	private final static Logger LOG = Logger.getLogger(TracingTab.class.getName());

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
		
		Button button1 = UIFactory.createButton(ButtonSize.SMALL, ButtonStyle.BORDERLESS,"Send FWB");
		Button button2 = UIFactory.createButton(ButtonSize.SMALL, ButtonStyle.BORDERLESS,"Print");
		Button button3 = UIFactory.createButton(ButtonSize.SMALL, ButtonStyle.BORDERLESS,"CUC & MUC");
		
		parent.addComponent(button1);
		parent.addComponent(button2);
		parent.addComponent(button3);
		
		parent.setComponentAlignment(button1, Alignment.MIDDLE_RIGHT);
		parent.setComponentAlignment(button2, Alignment.MIDDLE_RIGHT);
		parent.setComponentAlignment(button3, Alignment.MIDDLE_RIGHT);
		
		return parent;
	}

	private Table createTableLastStatus() {
		statusTable = new Table("Status");
		statusTable.setId("booking-table");
		
		statusTable.addContainerProperty(STATUS, String.class, null);
		statusTable.addContainerProperty(DATE, String.class, null);
		statusTable.addContainerProperty(REMARK, String.class, null);
				
		statusTable.setWidth(100, Unit.PERCENTAGE);
		statusTable.setHeight(100, Unit.PIXELS);
		
		statusTable.setColumnHeader(STATUS, "Status");
		statusTable.setColumnHeader(DATE, "Date");
		statusTable.setColumnHeader(REMARK, "Remark");
		
		return statusTable;
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
		
//		tPiecesL.setValue("1 pieces");
//		tweightL.setValue("1.0 kg");
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
		
		awb1 = new TextField();
		awb2 = new TextField();
		awb3 = new TextField();
		
		awb1.setWidth(56, Unit.PIXELS);
		awb2.setWidth(63, Unit.PIXELS);
		awb3.setWidth(63, Unit.PIXELS);
		
		Button search = UIFactory.createButton(ButtonSize.NORMAL, ButtonStyle.PRIMARY, "Search");
		parent.addComponent(awb1);
		parent.addComponent(awb2);
		parent.addComponent(awb3);
		parent.addComponent(search);
		search.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String ca3dg = awb1.getValue();
				String awbStock = awb2.getValue();
				String awbNo = awb3.getValue();
				if(!ca3dg.equals("") && !awbStock.equals("") && !awbNo.equals("") ){
					BookingConfirmation result = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getTracingShipmentInfo(ca3dg, awbStock, awbNo);
					
					initData(result);
				}else{
					Notification.show("Please input awb no", Type.ERROR_MESSAGE);
					return;
				}
			}
		});;
		
//		root.addComponent(awbNoLabel);
		root.addComponent(parent);
		root.setComponentAlignment(parent, Alignment.MIDDLE_CENTER);
//		root.setExpandRatio(awbNoLabel, 0.0f);
		root.setExpandRatio(parent, 1.0f);
		
		return root;
	}

	public void initData(BookingConfirmation bookingData) {
		awb1.setValue(bookingData.getCa3dg());
		awb2.setValue(bookingData.getAwbStock());
		awb3.setValue(bookingData.getAwbNo());
		LinkedList<Status> status = bookingData.getStatusInformation();
		statusTable.removeAllItems();
		for(Status s: status){
			Item i = statusTable.addItem(s);
			i.getItemProperty(STATUS).setValue(s.getStatus());
			i.getItemProperty(DATE).setValue(s.getDate());
			i.getItemProperty(REMARK).setValue(s.getRemark());
		}
		List<CommodityItem> commodities = bookingData.getItemDetails();
		double pieces = 0;
		double weight = 0;

		for(CommodityItem item : commodities){
			pieces = pieces + Double.parseDouble(item.getPieces());
			weight = weight + Double.parseDouble(item.getWeight());
		}
		tPiecesL.setValue(String.valueOf(pieces));
		tweightL.setValue(String.valueOf(weight));
		shipperL.setValue(bookingData.getShipper().getName());
		consigneeL.setValue(bookingData.getConsignee().getName());
		
	}


}
