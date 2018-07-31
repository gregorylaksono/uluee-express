package org.uluee.web.component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.xerces.xs.ItemPSVI;
import org.uluee.web.Uluee_expressUI;
import org.uluee.web.booking.CheckboxAndBasketLayout;
import org.uluee.web.booking.DeptDestLayout;
import org.uluee.web.booking.AutoCompleteGoogleLayout;
import org.uluee.web.booking.ItemDescriptionLayout;
import org.uluee.web.booking.TracingTab;
import org.uluee.web.cloud.IModalWindowBridge;
import org.uluee.web.cloud.model.BookingComponent;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.CommodityWrapper;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;
import org.uluee.web.component.window.CommodityTableLayout;
import org.uluee.web.component.window.DisclaimerWindow;
import org.uluee.web.component.window.GoogleMapNewDestLayout;
import org.uluee.web.util.Constant;
import org.uluee.web.util.NavigatorConstant;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;
import org.uluee.web.util.Util;
import org.vaadin.ui.NumberField;

import com.google.gwt.text.client.DoubleParser;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;


public class MainPage extends VerticalLayout implements View, IModalWindowBridge{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2045062985535367445L;


	private DeptDestLayout deptDestLayout;
	private AutoCompleteGoogleLayout secondRowLayout;
	private ItemDescriptionLayout itemDescriptionLayout;
	private CheckboxAndBasketLayout topLayout;
	private TracingTab tracing;
	private TabSheet bookingTab;


	private String sessionId;


	private Map<String, List<String>> airports;

	@Override
	public void enter(ViewChangeEvent event) {
		if(((Uluee_expressUI)UI.getCurrent()).getUser() != null){
			this.sessionId = ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
		}
		createContents();
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		String params = event.getParameters();
		if(params.contains("tracing")){
			bookingTab.setSelectedTab(tracing);
			tracing.initData(((Uluee_expressUI)UI.getCurrent()).getBookingData());
		}
		checkAuthentication();
		if(sessionId != null){
			getFFWB();
			getAirportListAll();
			((Uluee_expressUI)UI.getCurrent()).removeAllWindows();
		}
	}

	private void getAirportListAll() {
		airports = ((Uluee_expressUI) UI.getCurrent()).getWebServiceCaller().getAirportList(Constant.caIdForAirport, sessionId, "");
		if(airports != null){
			List<String> from = airports.get("from");
			List<String> to = airports.get("to");
			for(String s: from){
				deptDestLayout.getDeptCb().addItem(s);
				int lgthString = s.length();
				String caption = new String(s.substring(lgthString - 5, lgthString - 2));
				deptDestLayout.getDeptCb().setItemCaption(s, caption);
			}
			for(String s: to){
				deptDestLayout.getDestCb().addItem(s);
				int lgthString = s.length();
				String caption = new String(s.substring(lgthString - 5, lgthString - 2));
				deptDestLayout.getDestCb().setItemCaption(s, caption);
			}
		}
	}

	private void getFFWB() {

		String ffwId = ((Uluee_expressUI) UI.getCurrent()).getWebServiceCaller().getFFWB(sessionId); 
		((Uluee_expressUI)UI.getCurrent()).setFFwId(ffwId);
	}

	private void checkAuthentication() {
		if(sessionId == null) {
			UI.getCurrent().getNavigator().navigateTo(NavigatorConstant.LOGIN_PAGE);
		}

	}



	private void createContents() {
		bookingTab = createTab();
		addComponent(bookingTab);
		((Uluee_expressUI)UI.getCurrent()).setTracingTab(bookingTab);
	}

	private VerticalLayout createButtonSubmitLayout() {
		VerticalLayout parent = (VerticalLayout) UIFactory.createLayout(LayoutType.VERTICAL, SizeType.UNDEFINED, null, true);
		Button submitButton = new Button("Continue");
		submitButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
		submitButton.addStyleName(ValoTheme.BUTTON_SMALL);
		submitButton.addClickListener( x -> {
			BookingComponent bookingComponent = validateAllFields();
			if(bookingComponent == null) {
				return;
			}

			UI.getCurrent().addWindow(new DisclaimerWindow(bookingComponent));
		});

		parent.addComponent(submitButton);
		parent.setComponentAlignment(submitButton, Alignment.MIDDLE_LEFT);
		return parent;
	}

	private BookingComponent validateAllFields() {
		if(deptDestLayout.getFromDate().getValue() == null) {
			Notification.show("Please insert from date", Type.ERROR_MESSAGE);return null;
		}
		if(secondRowLayout.getShipper() == null) {
			Notification.show("Please input the shipper from", Type.ERROR_MESSAGE);return null;
		}
		if(secondRowLayout.getConsignee() == null) {
			Notification.show("Please input the consignee to", Type.ERROR_MESSAGE);return null;
		}
		if(itemDescriptionLayout.getItemWeightField().getValue() == null) {
			Notification.show("Please input the item weight", Type.ERROR_MESSAGE);return null;
		}
		if(itemDescriptionLayout.getItemHeightField().getValue() == null) {
			Notification.show("Please input the item height", Type.ERROR_MESSAGE);return null;
		}
		if(itemDescriptionLayout.getItemLongField().getValue() == null) {
			Notification.show("Please input the item length", Type.ERROR_MESSAGE);return null;
		}
		if(itemDescriptionLayout.getCommodity() == null) {
			Notification.show("Please choose the commodity", Type.ERROR_MESSAGE);return null;
		}

		List<CommodityWrapper> comm = new ArrayList();
		if(itemDescriptionLayout.getCommodities().size() < 1) {
			CommodityWrapper c = new CommodityWrapper();
			Commodity commodity = itemDescriptionLayout.getCommodity();
			c.setHeight(new Double(itemDescriptionLayout.getItemHeightField().getValue()).intValue()).
			setLength(new Double(itemDescriptionLayout.getItemLongField().getValue()).intValue()).
			setPieces(new Double(itemDescriptionLayout.getItemPieceField().getValue()).intValue()).
			setWeight(new Double(itemDescriptionLayout.getItemWeightField().getValue())).
			setWidth(new Double(itemDescriptionLayout.getItemWidthField().getValue()).intValue()).

			setVolume(new Double(itemDescriptionLayout.getItemHeightField().getValue())* 
					new Double(itemDescriptionLayout.getItemLongField().getValue()) *
					new Double(itemDescriptionLayout.getItemWidthField().getValue())).
			setAnn_id(commodity.getAnnId()).setCom_id(String.valueOf(commodity.getCommId())).
			setCommodity(commodity.getCommName()).setScc(commodity.getSccCode()==null?"N!+@A":commodity.getSccCode());
			comm.add(c);

			itemDescriptionLayout.setCommodities(comm);

		}
		BookingComponent commodity = constructItemFormat(comm);
		LinkedHashMap<String, Object> param = buildRequestParam(commodity);
		commodity.setParam(param);
		return commodity;

	}


	private LinkedHashMap<String, Object> buildRequestParam(BookingComponent c) {
		LinkedHashMap<String, Object> param = new LinkedHashMap<>();
		String[] stringCommodities =c.getStringCommodities().split("&&");

		Date toDate = null;

		Calendar cal  = Calendar.getInstance();
		cal.setTime(deptDestLayout.getFromDate().getValue());
		cal.add(Calendar.DATE, 1);
		toDate = cal.getTime();

		//Get schedules
		param.put("sessionId",((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		param.put("airline_ca_id", Constant.caIdForAirport);
		param.put("airline_ca_3dg", Constant.ca3dgTemp);
		param.put("deprature", deptDestLayout.getDept());
		param.put("destination", deptDestLayout.getDest());
		param.put("minDeparture", Util.NORMAL_DATE_FORMAT.format(deptDestLayout.getFromDate().getValue()));
		param.put("maxArrival", Util.NORMAL_DATE_FORMAT.format(toDate));
		param.put("commodities",Arrays.asList(stringCommodities));

		//Additional params
		param.put("shipperId", secondRowLayout.getShipper().getCompanyID());	
		param.put("consigneeType", secondRowLayout.getConsignee().getType());
		param.put("consigneeParentId", secondRowLayout.getConsignee().getParentID());
		String tempConsignee = secondRowLayout.getConsignee().getCompanyID()+"|"+secondRowLayout.getConsignee().getParentID()+"|"+secondRowLayout.getConsignee().getType()+"|"+"false";
		param.put("consignee",tempConsignee);
		param.put("agentId", ((Uluee_expressUI)UI.getCurrent()).getFfwId());
		param.put("shipperId", secondRowLayout.getShipper().getCompanyID());

		//		
		//		param.put("latitudeShipper", deptDestLayout.getShipper().getLatitude());	
		//		param.put("longitudeShipper", deptDestLayout.getShipper().getLongitude());	
		//		param.put("consigneeId", deptDestLayout.getConsignee().getCompanyID());
		//		param.put("latitudeConsignee", deptDestLayout.getConsignee().getLatitude());	
		//		param.put("longitudeConsignee", deptDestLayout.getShipper().getLongitude());
		return param;
	}

	private BookingComponent constructItemFormat(List<CommodityWrapper> tempList) {
		BookingComponent bookingComponent = new BookingComponent();
		Integer totalPieces = 0;
		Double totalWeight = 0.0;
		Double totalVolume = 0.0;
		Double volumeWeight = 0.0;

		//		String VolumeTotal= null;
		//		String WeightVolume = null;	
		String insurance = null;

		String stringCommodities = "";
		//totalPieces, totalWeight,totalVolume,volumeWeight
		for(int i = 0; i < tempList.size(); i++) {
			totalPieces = totalPieces + tempList.get(i).getPieces();
			totalWeight = totalWeight
					+ (tempList.get(i).getWeight() * tempList
							.get(i).getPieces());
			totalVolume = totalVolume
					+ (tempList.get(i).getVolume() * tempList
							.get(i).getPieces());						
			volumeWeight = volumeWeight
					+ ((tempList.get(i).getVolume() * tempList.get(
							i).getPieces()) / 0.006);

			DecimalFormat df = new DecimalFormat("#.##");


			Integer tempLength = tempList.get(i).getLength();
			Integer tempWidth = tempList.get(i).getWidth();
			Integer tempHeight = tempList.get(i).getHeight();
			Double tempInsurance = tempList.get(i).getInsurance();
			String sInsurance = "";

			if(tempInsurance != null){
				sInsurance = df.format(tempInsurance);							
			}

			if (tempLength == null && tempWidth == null
					&& tempHeight == null
					&& tempList.get(i).getVolume() != null) {
				tempLength = 0;
				tempWidth = 0;
				tempHeight = 0;
			}
			if(tempList.get(i).getCom_id() == null){
				stringCommodities = stringCommodities + "&&"
						+ String.valueOf(tempList.get(i).getCommodity() + ":"
								+ "0" +":"+tempList.get(i).getCommodity()).trim()+ "|"
								+ tempList.get(i).getScc() + "|"
								+ tempList.get(i).getPieces() + "|" + "Each"
								+ "|" + tempList.get(i).getWeight() + "|"
								+ tempLength + "|" + tempWidth + "|"
								+ tempHeight + "|"
								+ tempList.get(i).getVolume()
								+ "|"+sInsurance+"| | | | | | ";
			}					
			else{
				stringCommodities = stringCommodities + "&&"
						+ String.valueOf(tempList.get(i).getCom_id()+ ":"
								+ tempList.get(i).getAnn_id()
								+ ":"+tempList.get(i).getCommodity()).trim() + "|"
								+ tempList.get(i).getScc() + "|"
								+ tempList.get(i).getPieces() + "|" + "Each"
								+ "|" + tempList.get(i).getWeight() + "|"
								+ tempLength + "|" + tempWidth + "|"
								+ tempHeight + "|"
								+ tempList.get(i).getVolume()
								+ "|"+sInsurance+"| | | | | | ";
			}
		}
		stringCommodities = stringCommodities.substring(2);

		bookingComponent.setInsurance(insurance).
		setStringCommodities(stringCommodities).
		setTotalPieces(totalPieces).
		setTotalVolume(totalVolume).
		setTotalWeight(totalWeight).
		setVolumeWeight(volumeWeight);
		return bookingComponent;
	}

	private TabSheet createTab() {
		tracing = new TracingTab("Tracing");
		topLayout = new CheckboxAndBasketLayout();
		topLayout.getBasketButton().addClickListener(e->{
			UIFactory.addWindow(new CommodityTableLayout(MainPage.this, itemDescriptionLayout.getCommodities()), false, false, false, true);
		});
		deptDestLayout = new DeptDestLayout();
		itemDescriptionLayout = new ItemDescriptionLayout();
		secondRowLayout = new AutoCompleteGoogleLayout();
		VerticalLayout buttonSubmitLayout = createButtonSubmitLayout();
		secondRowLayout.getDeptField().setSuggestionPickedListener(e->{
			if(e.isNotSaved()) {
				secondRowLayout.getDeptSignLabel().addStyleName("warning-sign");
				UI.getCurrent().addWindow(new GoogleMapNewDestLayout(GoogleMapNewDestLayout.SHIPPER, MainPage.this, e.getCompanyName()));
			}else {
				secondRowLayout.getDeptSignLabel().addStyleName("check-sign");
				secondRowLayout.setShipper(e);
			}
		});

		secondRowLayout.getDestField().setSuggestionPickedListener(e->{
			if(e.isNotSaved()) {
				secondRowLayout.getDestSignLabel().addStyleName("warning-sign");
				UIFactory.addWindow(new GoogleMapNewDestLayout(GoogleMapNewDestLayout.CONSIGNEE, MainPage.this, e.getCompanyName()),false, false, true, true);
			}else {
				secondRowLayout.getDestSignLabel().addStyleName("check-sign");
				secondRowLayout.setConsignee(e);
			}
		});

		Label stripe1 = new Label("<hr>"); 
		Label stripe2 = new Label("<hr>"); 

		stripe1.setContentMode(ContentMode.HTML);
		stripe2.setContentMode(ContentMode.HTML);

		TabSheet tab = new TabSheet();
		tab.setHeight(100, Unit.PERCENTAGE);
		tab.setWidth(100, Unit.PERCENTAGE);
		VerticalLayout content = new VerticalLayout();
		content.setCaption("Booking");
		content.setSizeFull();
		content.addComponent(topLayout);
		content.addComponent(deptDestLayout);
//		content.addComponent(stripe1);
		content.addComponent(itemDescriptionLayout);
//		content.addComponent(stripe2);
		content.addComponent(secondRowLayout);
		content.addComponent(buttonSubmitLayout);

		content.setExpandRatio(topLayout, 0.0f);
		content.setExpandRatio(deptDestLayout, 0.0f);
		content.setExpandRatio(secondRowLayout, 0.0f);
//		content.setExpandRatio(stripe2, 0.0f);
		content.setExpandRatio(itemDescriptionLayout, 0.0f);
		content.setExpandRatio(buttonSubmitLayout, 1.0f);
		content.setComponentAlignment(topLayout, Alignment.MIDDLE_RIGHT);
		tab.addTab(content);
		tab.addTab(tracing);
		return tab;
	}



	@Override
	public void react(Object... result) {
		Object o = result[0];
		if(o instanceof RSAddName){
			RSAddName tempAdd = null;
			tempAdd = (RSAddName) o;
			if(result.length > 1 && result[1] instanceof Integer){
				if(Integer.parseInt(String.valueOf(result[1])) == GoogleMapNewDestLayout.CONSIGNEE){
					secondRowLayout.setConsignee(tempAdd);
					secondRowLayout.getDestSignLabel().addStyleName("check-sign");
				}else{
					secondRowLayout.setShipper(tempAdd);
					secondRowLayout.getDeptSignLabel().addStyleName("check-sign");
				}
			}
		}
		else if(o instanceof List){
			this.itemDescriptionLayout.setCommodities((List<CommodityWrapper>) o);
		}

	}



}
