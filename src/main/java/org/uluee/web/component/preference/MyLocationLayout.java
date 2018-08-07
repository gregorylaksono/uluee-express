package org.uluee.web.component.preference;

import java.util.List;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.IModalWindowBridge;
import org.uluee.web.cloud.model.Preferences;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.component.RegisterPage;
import org.uluee.web.component.window.GoogleMapNewDestLayout;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;

public class MyLocationLayout extends VerticalLayout implements IModalWindowBridge{

	/**
	 * 
	 */
	private static final long serialVersionUID = 226427287666650499L;
	private AutocompleteField<RSAddName> company_name_tf;
	private TextField airport_tf;
	private TextField contact_person_tf;
	private TextField country_tf;
	private TextField street_tf;
	private TextField telephone_tf;
	private TextField email_tf;
	private ComboBox currency_tf;
	private String sessionId;
	private Preferences preferences;
	private RSAddName company_location;
	private ClickListener savePreferencesListener = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			validate();
			
			String airport = airport_tf.getValue();
			String contactPerson = contact_person_tf.getValue();
			String country = country_tf.getValue();
			String street = street_tf.getValue();
			String telp = telephone_tf.getValue();
			String email = email_tf.getValue();
			String currency = (String) currency_tf.getValue();
			Preferences p = new Preferences();
			p.setCompanyName(company_location.getCompanyName()).setAirportName(airport).
			setContactPerson(contactPerson).setCountry(country).setCountry(country).
			setEmail(email).setStreet(street).setTelephone(telp).setCurrency(currency).setAirportCode(preferences.getAirportCode());
			
			boolean result = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().savePreferences(p, sessionId);
			if(result){
				Notification.show("Data is saved", Type.HUMANIZED_MESSAGE);
			}else{
				Notification.show("Error saving the changes", Type.ERROR_MESSAGE);
			}
		}
	};
	

		
	public MyLocationLayout() {
		createContents();
		acquireData();
		if(preferences != null){
			initValues();
		}else{
			Notification.show("Can not retrieve data from server", Type.ERROR_MESSAGE);
		}
	}

	private void initValues() {
		company_name_tf.setValue(preferences.getCompanyName() != null ? preferences.getCompanyName():"");
		airport_tf.setValue(preferences.getAirportName()!= null ?preferences.getAirportName():"");
		contact_person_tf.setValue(preferences.getContactPerson()!= null ?preferences.getContactPerson():"");
		country_tf.setValue(preferences.getCountry()!= null ?preferences.getCountry():"");
		street_tf.setValue(preferences.getStreet()!= null ?preferences.getStreet():"");
		telephone_tf.setValue(preferences.getTelephone()!= null ?preferences.getTelephone():"");
		email_tf.setValue(preferences.getEmail()!= null ?preferences.getEmail():"");
		if(currency_tf.containsId(preferences.getCurrency())){
			currency_tf.select(preferences.getCurrency());
		}
	}

	private void acquireData() {
		this.sessionId = ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
		this.preferences = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getPreferences(sessionId);
	}

	private void createContents() {
		HorizontalLayout root = new HorizontalLayout();
		root.setWidth(100, Unit.PERCENTAGE);
		VerticalLayout col1 = new VerticalLayout();
		VerticalLayout col2 = new VerticalLayout();
		col1.setHeight(100, Unit.PERCENTAGE);
		col2.setHeight(100, Unit.PERCENTAGE);
		col1.setSpacing(true);
		col2.setSpacing(true);
		
		root.setSpacing(true);
		root.setMargin(true);
		
		company_name_tf = createCompanyAutoCompleteComponent("Company Name");
		airport_tf = new TextField("Airport");
		contact_person_tf = new TextField("Contact person");
		country_tf = new TextField("Country");
		street_tf = new TextField("Street");
		telephone_tf = new TextField("Telephone");
		email_tf =new TextField("Email");
		currency_tf = new ComboBox("Currency");
		
		initFormBehavior();
		
		col1.addComponent(company_name_tf);
		col1.addComponent(airport_tf);
		col1.addComponent(contact_person_tf);
		col1.addComponent(country_tf);
		
		col2.addComponent(street_tf);
		col2.addComponent(telephone_tf);
		col2.addComponent(email_tf);
		col2.addComponent(currency_tf);
		
		currency_tf.setInvalidAllowed(false);
		currency_tf.setNullSelectionAllowed(false);
		currency_tf.addItem("USD");
		currency_tf.addItem("EUR");
		
		root.addComponent(col1);
		root.addComponent(col2);
		root.setComponentAlignment(col1, Alignment.MIDDLE_LEFT);
		root.setComponentAlignment(col2, Alignment.MIDDLE_RIGHT);
		
		Button saveButton = new Button("Save");
		Button resetButton = new Button("Reset");
		
		saveButton.addStyleName(ValoTheme.BUTTON_SMALL);
		saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		saveButton.addClickListener(savePreferencesListener );
		
		resetButton.addStyleName(ValoTheme.BUTTON_SMALL);
		resetButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		resetButton.addClickListener(e->{
			initValues();
		});
		HorizontalLayout l = new HorizontalLayout();
		l.setSpacing(true);
		l.setMargin(true);
		l.addComponent(saveButton);
		l.addComponent(resetButton);
		
		col2.addComponent(l);
		
		addComponent(root);
		setSpacing(true);
		setMargin(true);
	}
	private AutocompleteField<RSAddName> createCompanyAutoCompleteComponent(String caption) {
		AutocompleteField<RSAddName> field = new AutocompleteField<>();
		field.setCaption(caption);
		field.setQueryListener(new AutocompleteQueryListener<RSAddName>() {

			@Override
			public void handleUserQuery(AutocompleteField<RSAddName> arg0, String arg1) {
				List<String> rsult = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getGoogleAutocomplete(arg1);
				for(String s:rsult) {
					RSAddName addName = new RSAddName();
					addName.setCompanyName(s).setNotSaved(true);
					arg0.addSuggestion(addName, s);

				}
			}
		});

		field.setSuggestionPickedListener(e->{
			if(e.isNotSaved()) {
				((Uluee_expressUI)UI.getCurrent()).addWindow(new GoogleMapNewDestLayout(GoogleMapNewDestLayout.CONSIGNEE, MyLocationLayout.this, e.getCompanyName()));
			}
			company_location = e;
			if(company_location.getStreet()!=null){
				street_tf.setValue(company_location.getStreet());
			}
			if(company_location.getCountry()!=null){
				country_tf.setValue(company_location.getCountry());
			}
		});
		return field;
	}
	private void validate(){
		company_name_tf.validate();
		airport_tf.validate();
		contact_person_tf.validate();
		country_tf.validate();
		street_tf.validate();
		telephone_tf.validate();
		email_tf.validate();
		currency_tf.validate();
	}

	private void initFormBehavior() {
		company_name_tf.setRequired(true);
		airport_tf.setRequired(true);
		contact_person_tf.setRequired(true);
		street_tf.setRequired(true);
		email_tf.setRequired(true);
		currency_tf.setRequired(true);
		
		airport_tf.setValidationVisible(true);
		contact_person_tf.setValidationVisible(true);
		street_tf.setValidationVisible(true);
		email_tf.setValidationVisible(true);
		currency_tf.setValidationVisible(true);
		
		airport_tf.addValidator(new StringLengthValidator("Not valid", 2, 40, false));
		street_tf.addValidator(new StringLengthValidator("Not valid", 3, 40, false));
		email_tf.addValidator(new StringLengthValidator("Not valid", 3, 40, false));
		currency_tf.addValidator(new StringLengthValidator("Not valid", 3, 40, false));
	}

	@Override
	public void react(Object... result) {
		this.company_location = (RSAddName)result[0];
		
	}

}
