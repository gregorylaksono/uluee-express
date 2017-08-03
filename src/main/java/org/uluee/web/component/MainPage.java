package org.uluee.web.component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.xerces.xs.ItemPSVI;
import org.uluee.web.Uluee_expressUI;
import org.uluee.web.booking.CheckboxAndBasketLayout;
import org.uluee.web.booking.DeptDestLayout;
import org.uluee.web.booking.FromAndToDateLayout;
import org.uluee.web.booking.ItemDescriptionLayout;
import org.uluee.web.booking.TracingTab;
import org.uluee.web.cloud.IModalWindowBridge;
import org.uluee.web.cloud.model.BookingComponent;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.CommodityWrapper;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;
import org.uluee.web.component.window.CommodityTableLayout;
import org.uluee.web.component.window.DisclaimerLayout;
import org.uluee.web.component.window.GoogleMapNewDestLayout;
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


	private ItemDescriptionLayout itemDescriptionLayout;
	private CheckboxAndBasketLayout topLayout;
	private FromAndToDateLayout dateLayout;
	private DeptDestLayout deptDestLayout;
	
	@Override
	public void enter(ViewChangeEvent event) {

		createContents();
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		initFieldValue();
	}

	private void initFieldValue() {

		topLayout.getBookingOption().select("Deprature");
	}

	private void createContents() {
		TabSheet bookingTab = createTab();
		addComponent(bookingTab);
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
			Panel p = new Panel();
			p.setContent(new DisclaimerLayout(bookingComponent));
			p.setHeight(500, Unit.PIXELS);
			Window w = new Window();
			w.setContent(p);
			w.setClosable(true);
			w.setModal(true);
			w.setDraggable(false);
			w.setResizable(false);
			w.center();
			UI.getCurrent().addWindow(w);
		});

		parent.addComponent(submitButton);
		parent.setComponentAlignment(submitButton, Alignment.MIDDLE_LEFT);
		return parent;
	}

	private BookingComponent validateAllFields() {
		if(dateLayout.getFromDate().getValue() == null) {
			Notification.show("Please insert from date", Type.ERROR_MESSAGE);return null;
		}
		if(dateLayout.getToDate().getValue() == null) {
			Notification.show("Please insert to date", Type.ERROR_MESSAGE);return null;
		}
		if(deptDestLayout.getShipper() == null) {
			Notification.show("Please input the shipper from", Type.ERROR_MESSAGE);return null;
		}
		if(deptDestLayout.getConsignee() == null) {
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
			setCommodity(commodity.getCommName()).setScc(commodity.getSccCode());
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

		param.put("sessionId",((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		param.put("shipperName", deptDestLayout.getShipper().getCompanyName() );
		param.put("consigneeName", deptDestLayout.getConsignee().getCompanyName());	
		param.put("minDep", Util.NORMAL_DATE_FORMAT.format(dateLayout.getFromDate().getValue()));
		param.put("maxArr", Util.NORMAL_DATE_FORMAT.format(dateLayout.getToDate().getValue()));

		for (int i = 0; i < stringCommodities.length; i++) {
			param.put("commodities",stringCommodities[i]);
		}

		param.put("shipperAddId", deptDestLayout.getShipper().getCompanyID());	
		param.put("latitudeShipper", deptDestLayout.getShipper().getLatitude());	
		param.put("longitudeShipper", deptDestLayout.getShipper().getLongitude());	
		param.put("consigneeAddId", deptDestLayout.getConsignee().getCompanyID());
		param.put("latitudeConsignee", deptDestLayout.getConsignee().getLatitude());	
		param.put("longitudeConsignee", deptDestLayout.getShipper().getLongitude());
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
						+ tempList.get(i).getCommodity() + ":"
						+ "0" +":"+tempList.get(i).getCommodity()+ "|"
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
						+ tempList.get(i).getCom_id()+ ":"
						+ tempList.get(i).getAnn_id()
						+ ":"+tempList.get(i).getCommodity() + "|"
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
		topLayout = new CheckboxAndBasketLayout();
		topLayout.getBasketButton().addClickListener(e->{
			UIFactory.addWindow(new CommodityTableLayout(MainPage.this, itemDescriptionLayout.getCommodities()), false, false, true);
		});
		dateLayout = new FromAndToDateLayout();
		deptDestLayout = new DeptDestLayout();
		itemDescriptionLayout = new ItemDescriptionLayout();
		VerticalLayout buttonSubmitLayout = createButtonSubmitLayout();
		deptDestLayout.getDeptField().setSuggestionPickedListener(e->{
			if(e.isNotSaved()) {
				deptDestLayout.getDeptSignLabel().addStyleName("warning-sign");
				UIFactory.addWindow(new GoogleMapNewDestLayout(GoogleMapNewDestLayout.SHIPPER, MainPage.this, e.getCompanyName()), false, true, true);
			}else {
				deptDestLayout.getDeptSignLabel().addStyleName("check-sign");
				deptDestLayout.setShipper(e);
			}
		});
		
		deptDestLayout.getDestField().setSuggestionPickedListener(e->{
			if(e.isNotSaved()) {
				deptDestLayout.getDestSignLabel().addStyleName("warning-sign");
				UIFactory.addWindow(new GoogleMapNewDestLayout(GoogleMapNewDestLayout.CONSIGNEE, MainPage.this, e.getCompanyName()), false, true, true);
			}else {
				deptDestLayout.getDestSignLabel().addStyleName("check-sign");
				deptDestLayout.setConsignee(e);
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
		content.addComponent(dateLayout);
		content.addComponent(deptDestLayout);
		content.addComponent(stripe1);
		content.addComponent(itemDescriptionLayout);
		content.addComponent(stripe2);
		content.addComponent(buttonSubmitLayout);

		content.setExpandRatio(topLayout, 0.0f);
		content.setExpandRatio(dateLayout, 0.0f);
		content.setExpandRatio(deptDestLayout, 0.0f);
		content.setExpandRatio(stripe1, 0.0f);
		content.setExpandRatio(stripe2, 0.0f);
		content.setExpandRatio(itemDescriptionLayout, 0.0f);
		content.setExpandRatio(buttonSubmitLayout, 1.0f);
		tab.addTab(content);
		tab.addTab(new TracingTab("Tracing"));
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
					deptDestLayout.setConsignee(tempAdd);
					deptDestLayout.getDestSignLabel().addStyleName("check-sign");
				}else{
					deptDestLayout.setShipper(tempAdd);
					deptDestLayout.getDeptSignLabel().addStyleName("check-sign");
				}
			}
		}
		else if(o instanceof List){
			this.itemDescriptionLayout.setCommodities((List<CommodityWrapper>) o);
		}
		
	}



}
