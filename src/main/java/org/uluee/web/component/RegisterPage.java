package org.uluee.web.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.IModalWindowBridge;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;
import org.uluee.web.component.window.GoogleMapNewDestLayout;
import org.uluee.web.component.window.TermAndConditionActernityWindow;
import org.uluee.web.util.Constant;
import org.uluee.web.util.NavigatorConstant;

import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;

public class RegisterPage extends VerticalLayout implements View, IModalWindowBridge {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1802742159581725527L;
	private List<String> currencies = new ArrayList();
	private RSAddName locationSelected;
//	private TextField company;
	private TextField firstname;
	private ComboBox gender;
	private ComboBox currency;
	private TextField lastname;
	private TextField telp;
	private AutocompleteField<RSAddName> company;
	private CheckBox okCb;
	private ClickListener registerListener = new ClickListener() {
		
		@Override
		public void buttonClick(ClickEvent event) {
			validateFields();
			String companyNameValue = locationSelected.getCompanyName();
			String country = locationSelected.getCountry();
			String countryId = locationSelected.getCountryId();
			String address = locationSelected.getStreet();
			String street  = locationSelected.getStreet();;
			String city = locationSelected.getCity();
			String longitude = locationSelected.getLongitude();
			String latitude = locationSelected.getLatitude();
			String emailValue = emailAddress;
			String firstNameValue = firstname.getValue();
			String lastNameValue = lastname.getValue();
			String selectedCur = (String) currency.getValue();
			String selectedGen = (String) gender.getValue();
			
			String tempSignUp = companyNameValue+"|"+firstNameValue+"|"+lastNameValue+
					"|"+emailValue+"|"+selectedCur+"|"+"TXL"+"|"+country+"|"+countryId+"|"+"s"+"|"+""+"|"+""+"|"+selectedGen+"|"+address+"|"+
					"cp"+"|"+street+"|"+"s"+"|"+city+"|"+"c"+"|"+""+"|"+""+"|"+""+"|"+""+"|"+""+"|"+""+"|"+emailValue+"|"+
					"e"+"|"+longitude+"|"+latitude+"|"+"empty";
			
			boolean result = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().register(tempSignUp);
			if(result){
				Notification.show("Registration", "Registration success", Type.HUMANIZED_MESSAGE);
				UI.getCurrent().getNavigator().navigateTo(NavigatorConstant.LOGIN_PAGE);
			}else{
				Notification.show("Registration is failed", Type.ERROR_MESSAGE);
			}
		}
	};
	private String emailAddress;
	public RegisterPage() {
		setSpacing(true);
		setMargin(true);
		createContents();
		createLayout();
	}

	private void createLayout() {
		Panel regPanel = new Panel();
		regPanel.setWidth(100, Unit.PERCENTAGE);
		regPanel.setCaption("Sign up");
		HorizontalLayout fieldLayout = new HorizontalLayout();
		fieldLayout.setWidth(100, Unit.PERCENTAGE);
		fieldLayout.setMargin(true);
		fieldLayout.setSpacing(true);
		VerticalLayout col1 = new VerticalLayout();
		VerticalLayout col2 = new VerticalLayout();

		col1.setSpacing(true);
		col2.setSpacing(true);

		col1.addComponent(company);
		col1.addComponent(firstname);
		col1.addComponent(gender);

		col2.addComponent(currency);
		col2.addComponent(lastname);
		col2.addComponent(telp);

		fieldLayout.addComponent(col1);
		fieldLayout.addComponent(col2);
		
		fieldLayout.setComponentAlignment(col1, Alignment.TOP_LEFT);
		fieldLayout.setComponentAlignment(col2, Alignment.TOP_RIGHT);

		Button termButton = new Button("Terms and Conditions of Service.");
		termButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
		termButton.addStyleName(ValoTheme.BUTTON_SMALL);
		termButton.addClickListener(e->{
			Window w = new TermAndConditionActernityWindow();
			UI.getCurrent().addWindow(w);
		});
		
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		
		Button submitButton = new Button("Register");
		submitButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		submitButton.addStyleName(ValoTheme.BUTTON_SMALL);
		submitButton.setEnabled(false);
		submitButton.addClickListener(registerListener);
		
		Button backButton = new Button("Login");
		backButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		backButton.addStyleName(ValoTheme.BUTTON_SMALL);
		backButton.addClickListener( e->{
			UI.getCurrent().getNavigator().navigateTo(NavigatorConstant.LOGIN_PAGE);
		});
		
		buttonLayout.addComponent(submitButton);
		buttonLayout.addComponent(backButton);
		okCb = new CheckBox("By clicking the Register button below, I certify that I have read and agree to the Acternity");
		okCb.addStyleName(ValoTheme.CHECKBOX_SMALL);
		okCb.setImmediate(true);
		okCb.addValueChangeListener(e->{
			boolean isSelected = (boolean) e.getProperty().getValue();
			if(isSelected){
				submitButton.setEnabled(true);
			}
		});
		CssLayout cbWrapper = new CssLayout();
		cbWrapper.addComponent(okCb);
		cbWrapper.addComponent(termButton);
		cbWrapper.addStyleName("registerwrapper");
		
		regPanel.setContent(fieldLayout);
		addComponent(regPanel);
		addComponent(cbWrapper);
		addComponent(buttonLayout);
	}

	private void createContents() {
		User r = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().login(Constant.USERNAME, Constant.PASSWORD);
		((Uluee_expressUI)UI.getCurrent()).setUser(r);
		if(r!=null || r.getSessionId() !=null){
			currencies.addAll(((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getCurrencies(r.getSessionId()));
		}else{
			currencies.add("EUR");
			currencies.add("USD");
		}
		company = createCompanyAutoCompleteComponent("Company Name");
		firstname = new TextField("First Name");
		gender = new ComboBox("Gender");
		currency = new ComboBox("Currency");
		lastname = new TextField("Last Name");
		telp = new TextField("Telephone");
		
		company.setRequired(true);
		firstname.setRequired(true);
		gender.setRequired(true);
		currency.setRequired(true);
		lastname.setRequired(true);
		telp.setRequired(true);

		company.setValidationVisible(true);
		firstname.setValidationVisible(true);
		gender.setValidationVisible(true);
		currency.setValidationVisible(true);
		lastname.setValidationVisible(true);
		telp.setValidationVisible(true);
		
		company.addValidator(new StringLengthValidator("Not valid", 3, 40, false));
		firstname.addValidator(new StringLengthValidator("Not valid", 3, 40, false));
		lastname.addValidator(new StringLengthValidator("Not valid", 3, 40, false));
		telp.addValidator(new RegexpValidator("[0-9]+", "Not valid"));
		initComboBox();
	}
	
	private void validateFields(){
		firstname.validate();
		gender.validate();
		currency.validate();
		lastname.validate();
		telp.validate();
	}

	private void initComboBox() {
		currency.setNullSelectionAllowed(false);
		for(String c: currencies){
			String id = c.split(" ")[0];
			currency.addItem(id);
			currency.setItemCaption(id, c);
		}
		
		gender.setNullSelectionAllowed(false);
		gender.addItem("m");
		gender.addItem("f");
		gender.setItemCaption("m", "Male");
		gender.setItemCaption("f", "Female");
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
				((Uluee_expressUI)UI.getCurrent()).addWindow(new GoogleMapNewDestLayout(GoogleMapNewDestLayout.CONSIGNEE, RegisterPage.this, e.getCompanyName()));
			}
		});
		return field;
	}
	@Override
	public void enter(ViewChangeEvent event) {

	}

	@Override
	public void react(Object... result) {
		this.locationSelected = (RSAddName) result[0];
		this.emailAddress = (String) result[2];
	}

}
