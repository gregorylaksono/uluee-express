package org.uluee.web.component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.BookingConfirmation;
import org.uluee.web.cloud.model.CommodityItem;
import org.uluee.web.cloud.model.Flight;
import org.uluee.web.cloud.model.FlightSchedule;
import org.uluee.web.cloud.model.PaypalData;
import org.uluee.web.cloud.model.ScheduleDoorToDoor;
import org.uluee.web.util.Constant;
import org.uluee.web.util.NavigatorConstant;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.Util;

import com.vaadin.data.Item;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TreeTable;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
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
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	private Label tVolWeightL;
	private Label tvolumeL;
	private Label tweightL;
	private Label tPiecesL;
	private List<FlightSchedule> result;
	private TreeTable table;
	
	private String[] commodities;
	private String deprature;
	private String destination;
	private String consigneeId;
	private String consigneeType;
	private String shipperId;
	private String consigneeParentId;
	private String ffwId;
	private String dep;
	private String arr;


	@Override
	public void enter(ViewChangeEvent event) {
		if(event.getParameters() != null){
			LinkedHashMap<String, Object> param = new LinkedHashMap<>();
			String[] msgs = event.getParameters().split("/");
			
			try {
				String[] consignee = msgs[0].split(Pattern.quote("|"));
				this.consigneeId = consignee[0];
				this.consigneeType = consignee[2];
				this.consigneeParentId = consignee[1];
				this.shipperId = msgs[1];
				this.ffwId = msgs[2];
				
				dep = Util.CONVERT_DATE_FORMAT.format(Util.NORMAL_DATE_FORMAT.parse(msgs[3]));
				arr = Util.CONVERT_DATE_FORMAT.format(Util.NORMAL_DATE_FORMAT.parse(msgs[4]));
				
				this.commodities  = msgs[5].replace("!+@", "/").split("&&");
				this.deprature = msgs[6];
				this.destination = msgs[7];
				
				String sessionId = (String)((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
				result = ((Uluee_expressUI) UI.getCurrent()).getWebServiceCaller().getSchedules(
						sessionId, Constant.caIdTemp, Constant.ca3dgTemp, deprature, destination,
						dep, arr, commodities);
				createContents();
				insertItems();
			} catch (ParseException e) {
				e.printStackTrace();
			}



		}
	}
	private LinkedHashMap<String, Object> param;

	public BookingPage() {
		UI.getCurrent().getPage().setTitle("Schedule list");
		setMargin(true);
		setSpacing(true);
		setHeight(100, Unit.PERCENTAGE);
		
//		extractParam();
	}

//	private void extractParam() {
//		try {
//			String dep = Util.CONVERT_DATE_FORMAT.format(Util.NORMAL_DATE_FORMAT.parse((String)param.get("minDep")));
//			String arr = Util.CONVERT_DATE_FORMAT.format(Util.NORMAL_DATE_FORMAT.parse((String)param.get("maxArr")));
//			param.put("minDep",dep);
//			param.put("maxArr",arr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		result = ((Uluee_expressUI) UI.getCurrent()).getWebServiceCaller().getSchedules(param);
//		UIFactory.closeAllWindow();
//		insertItems();		
//	}

	private void createContents() {
		table = createTable();
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
		button.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				((Uluee_expressUI)UI.getCurrent()).getNavigator().navigateTo(NavigatorConstant.MAIN_PAGE);
			}
		});
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

//		tPiecesL.setValue("1 pieces");
//		tweightL.setValue("1.0 kg");
//		tvolumeL.setValue("1 m3");
//		tVolWeightL.setValue("0.17 kg");

		parent.addComponent(tPiecesL);
		parent.addComponent(tweightL);
		parent.addComponent(tvolumeL);
		parent.addComponent(tVolWeightL);
		return parent;
	}

	private TreeTable createTable() {
		TreeTable table = new TreeTable();
		table.addStyleName("uluee-table");
		table.addContainerProperty(DEP_TIME, String.class, null);
		table.addContainerProperty(ARR_TIME, String.class, null);
		table.addContainerProperty(RATE, String.class, null);
		table.addContainerProperty(SELECT, Button.class, null);

		table.setWidth(100, Unit.PERCENTAGE);
		table.setHeight(200, Unit.PIXELS);

		table.setColumnHeader(DEP_TIME, "Deprature time");
		table.setColumnHeader(ARR_TIME, "Arrival time");
		table.setColumnHeader(RATE, "Rate");
		table.setColumnHeader(SELECT, "");

		//		Item i = table.addItem("test");


		//		i.getItemProperty(DEP_TIME).setValue("21/07/17 17:00");
		//		i.getItemProperty(ARR_TIME).setValue("21/07/17 19:00");
		//		i.getItemProperty(RATE).setValue("200 EUR");
		//		i.getItemProperty(SELECT).setValue(b);
		return table;
	}
	private void insertItems() {
		int index = 1;
		boolean isItemProcessed = false;
		for(FlightSchedule schedule: result) {
			Button b = new Button("Select");
			if(!isItemProcessed) {
				describe(schedule);
				isItemProcessed = true;
			}
			b.setStyleName(ValoTheme.BUTTON_SMALL);
			b.addStyleName(ValoTheme.BUTTON_PRIMARY);

//			String[] args = schedule.getTotal_fee_from().split(" ");
//			String[] args = schedule.getTotalChargesTo().split(" ");
//			final Double rateFinal = new Double(schedule.getFlightList().get(0).getRate());
			Item parent = table.addItem(schedule);
			parent.getItemProperty(DEP_TIME).setValue("Flight "+index);
			for(Flight f :schedule.getFlightList()) {
				Item child = table.addItem(f);
				child.getItemProperty(DEP_TIME).setValue(format.format(f.getDepartureTime()));
				child.getItemProperty(ARR_TIME).setValue(format.format(f.getArrivalTime()));
				child.getItemProperty(RATE).setValue("");
				child.getItemProperty(SELECT).setValue(null);
				table.setParent(f, schedule);
			}
			parent.getItemProperty(ARR_TIME).setValue("");
			parent.getItemProperty(RATE).setValue(schedule.getFlightList().get(0).getRate()+" "+schedule.getFlightList().get(0).getCurrency());
			parent.getItemProperty(SELECT).setValue(b);
			index++;
			b.addClickListener(e->{
				String sessionId = ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
				String[] flights = constructFlights(schedule.getFlightList());
				String[] commodities = this.commodities;
				String shipperId = this.shipperId;
				String consignee = consigneeId+"|"+consigneeParentId+"|"+consigneeType+"|"+"false";
				String agentId = this.ffwId;
				String depDate = this.dep;
				BookingConfirmation bookingResult = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().book(sessionId, Constant.caIdTemp, 
						Constant.ca3dgTemp, flights, commodities,shipperId,consignee,agentId,depDate );
				((Uluee_expressUI)UI.getCurrent()).setBookingData(bookingResult);
				((Uluee_expressUI)UI.getCurrent()).getNavigator().navigateTo(NavigatorConstant.MAIN_PAGE+"/tracing");
			});
		}

	}

	private String[] constructFlights(List<Flight> flightList) {
		List<String> temp = new ArrayList();
		for(Flight flight: flightList){
			String tempFlight = flight.getCaId()+"|"+flight.getFltId()+"|"+sdf.format(flight.getDepartureTime())+
					"|"+sdf.format(flight.getArrivalTime())+"|"+flight.getMode();
			temp.add(tempFlight);
		}
		return temp.toArray(new String[temp.size()]);
	}

	private void describe(FlightSchedule schedule) {

		double weight = 0;
		double vol = 0;
		int pieces = 0;
		double height = 0;
		for(CommodityItem s: schedule.getCommodities()) {
			weight = Double.parseDouble(s.getWeight()) + weight;
			height = Double.parseDouble(s.getHeight()) + height;
			vol = (Double.parseDouble(s.getWidth()) * Double.parseDouble(s.getLength()) * Double.parseDouble(s.getHeight())) + vol;
			pieces = Integer.parseInt(s.getPieces()) + pieces; 
		}
		tvolumeL.setValue(String.valueOf(vol));
		tweightL.setValue(String.valueOf(weight));
		tPiecesL.setValue(String.valueOf(pieces));
		tVolWeightL.setValue(String.valueOf(height));
		
		//commodities,scc,pieces,each,weight,length,width,heigh,volume
	}

}
