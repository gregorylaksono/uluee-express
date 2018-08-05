package org.uluee.web.booking;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.booking.tracing.MrnCUCLayout;
import org.uluee.web.cloud.model.BookingConfirmation;
import org.uluee.web.cloud.model.CommodityItem;
import org.uluee.web.cloud.model.Status;
import org.uluee.web.cloud.model.User;
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
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
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
	private TextField ca3dgText;
	private TextField awbStockText;
	private TextField awbNoText;
	private String awbStock;
	private String ca3dg;
	private String awbNo;
	private Button searchAWb;
	private final static Logger LOG = Logger.getLogger(TracingTab.class.getName());
	private ClickListener sendFwbClickListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {
			String awbStock = awbStockText.getValue();
			String ca3dg = ca3dgText.getValue();
			String awbNo = awbNoText.getValue();
			if(awbStock.isEmpty() || ca3dg.isEmpty() || awbNo.isEmpty()){
				Notification.show("Please input awb no", Type.ERROR_MESSAGE);
				return;
			}
			User user = ((Uluee_expressUI)UI.getCurrent()).getUser();
			if(user != null){
				((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().sendFwb(user.getSessionId(), ca3dg, awbStock, awbNo);
			}else{
				Notification.show("User is not logged in", Type.ERROR_MESSAGE);
			}

		}
	};
	private ClickListener printListener = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			Window w = new Window();
			w.setModal(true);
			w.setResizable(false);
			w.setClosable(true);
			w.setContent(printLayout());
			UI.getCurrent().addWindow(w);
		}
	};
	private ClickListener cucMucListener = new ClickListener(){

		@Override
		public void buttonClick(ClickEvent event) {
			String awbStock = awbStockText.getValue();
			String ca3dg = ca3dgText.getValue();
			String awbNo = awbNoText.getValue();
			if(awbStock.isEmpty() || ca3dg.isEmpty() || awbNo.isEmpty()){
				Notification.show("Please input awb no", Type.ERROR_MESSAGE);
				return;
			}
			Window w = new Window();
			w.setModal(true);
			w.setResizable(false);
			w.setClosable(true);
			w.setContent(new MrnCUCLayout(ca3dg, awbStock, awbNo));
			UI.getCurrent().addWindow(w);
		}
		
	};
	
	public TracingTab(String caption) {
		setCaption(caption);
		init();
	}

	public TracingTab(String ca3dg, String awbStock, String awbNo) {
		this.ca3dg = ca3dg;
		this.awbStock = awbStock;
		this.awbNo = awbNo;

		setCaption("Tracing");
		init();

		if(this.ca3dg != null &&
				this.awbNo != null &&
				this.awbStock != null) {
			ca3dgText.setValue(ca3dg);
			awbStockText.setValue(awbStock);
			awbNoText.setValue(awbNo);
			searchAWb.click();
		}
	}

	private void init() {
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

		Button sendFwbButton = new Button("");
		Button printButton = new Button("");
		Button cucMucButton = new Button("");

		sendFwbButton.addStyleName(ValoTheme.BUTTON_SMALL);
		printButton.addStyleName(ValoTheme.BUTTON_SMALL);
		cucMucButton.addStyleName(ValoTheme.BUTTON_SMALL);

		sendFwbButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		printButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
		cucMucButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);

		sendFwbButton.addStyleName("tracking-button");
		printButton.addStyleName("tracking-button");
		cucMucButton.addStyleName("tracking-button");
		
		sendFwbButton.addClickListener(sendFwbClickListener);
		printButton.addClickListener(printListener);
		cucMucButton.addClickListener(cucMucListener );
		
		sendFwbButton.setIcon(new ThemeResource("images/sky_ico_fwbM.png"));
		printButton.setIcon(new ThemeResource("images/sky_ico_printM.png"));
		cucMucButton.setIcon(new ThemeResource("images/sky_ico_mrnM.png"));

		parent.addComponent(sendFwbButton);
		parent.addComponent(printButton);
		parent.addComponent(cucMucButton);

		parent.setComponentAlignment(sendFwbButton, Alignment.MIDDLE_RIGHT);
		parent.setComponentAlignment(printButton, Alignment.MIDDLE_RIGHT);
		parent.setComponentAlignment(cucMucButton, Alignment.MIDDLE_RIGHT);

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

		ca3dgText = new TextField();
		awbStockText = new TextField();
		awbNoText = new TextField();

		ca3dgText.setWidth(56, Unit.PIXELS);
		awbStockText.setWidth(63, Unit.PIXELS);
		awbNoText.setWidth(63, Unit.PIXELS);

		searchAWb = UIFactory.createButton(ButtonSize.NORMAL, ButtonStyle.PRIMARY, "Search");
		parent.addComponent(ca3dgText);
		parent.addComponent(awbStockText);
		parent.addComponent(awbNoText);
		parent.addComponent(searchAWb);
		searchAWb.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				String ca3dg = ca3dgText.getValue();
				String awbStock = awbStockText.getValue();
				String awbNo = awbNoText.getValue();
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
		ca3dgText.setValue(bookingData.getCa3dg());
		awbStockText.setValue(bookingData.getAwbStock());
		awbNoText.setValue(bookingData.getAwbNo());
		LinkedList<Status> status = bookingData.getStatusInformation();
		statusTable.removeAllItems();
		if(status == null) {
			searchAWb.click();
		}else {
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
	
	private VerticalLayout printLayout(){
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		Button printAwbButton = new Button();
		Button printBarcodeButton = new Button();
		Button printInvoiceButton = new Button();
		printAwbButton.addClickListener(e->{
			print(0);
		});
		printBarcodeButton.addClickListener(e->{
			print(2);
		});
		printInvoiceButton.addClickListener(e->{
			print(1);
		});
		l.addComponent(printAwbButton);
		l.addComponent(printBarcodeButton);
		l.addComponent(printInvoiceButton);
		
		return l;
	}
	


	

	private void print(int i) {
		String awbStock = awbStockText.getValue();
		String ca3dg = ca3dgText.getValue();
		String awbNo = awbNoText.getValue();
		User u = ((Uluee_expressUI)UI.getCurrent()).getUser();
		if(awbStock.isEmpty() || ca3dg.isEmpty() || awbNo.isEmpty()){
			Notification.show("Please input awb no", Type.ERROR_MESSAGE);
			return;
		}			
		String url = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().print(u.getSessionId(), ca3dg, awbStock, awbNo, 0);
		getUI().getPage().setLocation(url);		
	}


}
