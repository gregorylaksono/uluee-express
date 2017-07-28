package org.uluee.web.component.window;

import java.util.List;
import java.util.Map;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.IWebService;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;
import org.uluee.web.util.Constant;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;

public class GoogleMapNewDestLayout extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1351326827038424796L;
	private TextField companyNameText;
	private GoogleMap addressMap;


	public GoogleMapNewDestLayout() {
		createContents();
		setSpacing(true);
		setMargin(true);
		setHeight(600, Unit.PIXELS);
		setWidth(800, Unit.PIXELS);
	}

	private void createContents() {
		HorizontalLayout topLayout = createTopLayout();
		VerticalLayout gmapContainer = createGoogleMapLayout();
		addComponent(topLayout);
		addComponent(gmapContainer);

		setExpandRatio(topLayout, 0.0f);
		setExpandRatio(gmapContainer, 1.0f);
	}

	private VerticalLayout createGoogleMapLayout() {
		VerticalLayout parent = new VerticalLayout();
		parent.setWidth(100, Unit.PERCENTAGE);
		parent.setHeight(100, Unit.PERCENTAGE);
		addressMap = new GoogleMap(Constant.GMAP_API_KEY, null, "english");
//		addressMap.addMarker("NOT DRAGGABLE: MC Payment Indonesia", l, false, null);
		addressMap.setSizeFull();
		addressMap.setMinZoom(4);
		addressMap.setMaxZoom(16);
	
		parent.addComponent(addressMap);
		return parent;
	}

	private HorizontalLayout createTopLayout() {
		HorizontalLayout parent = new HorizontalLayout();
		parent.setHeight(80, Unit.PIXELS);
		parent.setWidth(100, Unit.PERCENTAGE);
		parent.setSpacing(true);

		Label text = new Label("GoogleMap");

		companyNameText = new TextField();
		AutocompleteField addressText = createCompanyAutoComplete();
		
		TextField emailText = new TextField();
		Button submit = new Button("Save");
		submit.setStyleName(ValoTheme.BUTTON_SMALL);
		submit.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		
		emailText.setWidth(120, Unit.PIXELS);
		companyNameText.setWidth(150, Unit.PIXELS);
		text.setWidth(null);
//		addressText.setWidth(300, Unit.PIXELS);
		
		companyNameText.setInputPrompt("Company");
		emailText.setInputPrompt("Email");
		parent.addComponent(text);
		parent.addComponent(companyNameText);
		parent.addComponent(addressText);
		parent.addComponent(emailText);
		parent.addComponent(submit);

		parent.setExpandRatio(text, 0.0f);
		parent.setExpandRatio(companyNameText, 0.0f);
		parent.setExpandRatio(addressText, 1.0f);
		parent.setExpandRatio(emailText, 0.0f);
		parent.setExpandRatio(submit, 0.0f);

		parent.setComponentAlignment(text, Alignment.MIDDLE_LEFT);
		parent.setComponentAlignment(companyNameText, Alignment.MIDDLE_LEFT);
		parent.setComponentAlignment(addressText, Alignment.MIDDLE_LEFT);
		parent.setComponentAlignment(emailText, Alignment.MIDDLE_RIGHT);
		parent.setComponentAlignment(submit, Alignment.MIDDLE_RIGHT);
		return parent;
	}

	private AutocompleteField createCompanyAutoComplete() {
		AutocompleteField<String> field = new AutocompleteField<>();
		field.setWidth(100, Unit.PERCENTAGE);
		field.setQueryListener(new AutocompleteQueryListener<String>() {

			@Override
			public void handleUserQuery(AutocompleteField<String> arg0, String arg1) {

				List<String> rsult = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getGoogleAutocomplete(arg1);

				for(String s:rsult) {
					arg0.addSuggestion(s, s);
				}
			}
		});
		field.setSuggestionPickedListener(e -> {
			Map  result = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getLatitudeLongitude(e);
			String latitude = (String) result.get(IWebService.LATITUDE);
			String longtitude = (String) result.get(IWebService.LONGTITUDE);
			String company = (String) result.get(IWebService.COMPANY);
			
			LatLon point = new LatLon(Double.parseDouble(latitude), Double.parseDouble(longtitude));
			addressMap.addMarker("NOT DRAGGABLE: "+e, point, false, null);
			addressMap.setCenter(point);
			
			companyNameText.setValue(company);
		});
		return field;
	}
}
