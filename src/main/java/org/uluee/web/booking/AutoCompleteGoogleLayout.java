package org.uluee.web.booking;

import java.util.List;
import java.util.Locale;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;

public class AutoCompleteGoogleLayout extends HorizontalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2328325726308818881L;
	
	private AutocompleteField<RSAddName> deptField;
	private AutocompleteField<RSAddName> destField;
	private Label deptSignLabel;
	private Label destSignLabel;
	private RSAddName shipper;
	private RSAddName consignee;
	
	public AutoCompleteGoogleLayout() {
		createContents();
	}

	private void createContents() {
		setWidth(100, Unit.PERCENTAGE);
		setMargin(new MarginInfo(true, false, true, false));
		setSpacing(true);
		setHeight(null);
		
		deptSignLabel = new Label();
		destSignLabel = new Label();
		
		deptSignLabel.setWidth(null);
		destSignLabel.setWidth(null);

		HorizontalLayout depFieldLayout = new HorizontalLayout();
		HorizontalLayout destFieldLayout = new HorizontalLayout();
		
		depFieldLayout.setWidth(100, Unit.PERCENTAGE);
		destFieldLayout.setWidth(100, Unit.PERCENTAGE);
		
		deptField = createShipperAutoCompleteComponent();
		destField = createConsigneeAutoCompleteComponent();
		deptField.setWidth(100, Unit.PERCENTAGE);
		destField.setWidth(100, Unit.PERCENTAGE);
		deptField.setCaption("Deprature");
		destField.setCaption("Destination");
		
		depFieldLayout.addComponent(deptField);
		depFieldLayout.addComponent(deptSignLabel);
		depFieldLayout.setExpandRatio(deptField, 1.0f);
		depFieldLayout.setExpandRatio(deptSignLabel, 0.0f);
		
		destFieldLayout.addComponent(destField);
		destFieldLayout.addComponent(destSignLabel);
		destFieldLayout.setExpandRatio(destField, 1.0f);
		destFieldLayout.setExpandRatio(destSignLabel, 0.0f);
		
		addComponent(depFieldLayout);
		addComponent(destFieldLayout);
	}
	
	
	private AutocompleteField<RSAddName> createShipperAutoCompleteComponent() {
		AutocompleteField<RSAddName> field = new AutocompleteField<>();
		field.setQueryListener(new AutocompleteQueryListener<RSAddName>() {

			@Override
			public void handleUserQuery(AutocompleteField<RSAddName> arg0, String arg1) {
				User user = ((Uluee_expressUI)UI.getCurrent()).getUser();
				List<RSAddName> dbData = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getShipperFfwAlsoNotifyDeliveredToAddressByMatchService(arg1, user.getSessionId());
				if(dbData.size() > 0) {
					for (RSAddName rSAddName : dbData) {
						String cityAdd = rSAddName.getCity();
						String countryAdd = rSAddName.getCountry().toString();
						String street = (", ").concat(rSAddName.getStreet());
						if( cityAdd.length() >= 2)
						{
							cityAdd = ","+" "+cityAdd.toString();
						}
						if(countryAdd.length() >= 2){
							countryAdd = ","+" "+countryAdd.toString();
						}
						arg0.addSuggestion(rSAddName,rSAddName.getCompanyName()+street+cityAdd+countryAdd);
						if (arg0.getState().suggestions.size() == 9) {
							break;
						}						
					}
				}
				else {
					List<String> rsult = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getGoogleAutocomplete(arg1);

					for(String s:rsult) {
						RSAddName addName = new RSAddName();
						addName.setCompanyName(s).setNotSaved(true);
						arg0.addSuggestion(addName, s);
					}

				}
			}
		});
		
		
		return field;
	}

	private AutocompleteField<RSAddName> createConsigneeAutoCompleteComponent() {
		AutocompleteField<RSAddName> field = new AutocompleteField<>();
		field.setQueryListener(new AutocompleteQueryListener<RSAddName>() {

			@Override
			public void handleUserQuery(AutocompleteField<RSAddName> arg0, String arg1) {
				User user = ((Uluee_expressUI)UI.getCurrent()).getUser();
				List<RSAddName> dbData = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getShipperFfwAlsoNotifyDeliveredToAddressByMatchService(arg1, user.getSessionId());
				if(dbData.size() > 0) {
					for (RSAddName rSAddName : dbData) {
						String cityAdd = rSAddName.getCity();
						String countryAdd = rSAddName.getCountry().toString();
						String street = (", ").concat(rSAddName.getStreet());
						if( cityAdd.length() >= 2)
						{
							cityAdd = ","+" "+cityAdd.toString();
						}
						if(countryAdd.length() >= 2){
							countryAdd = ","+" "+countryAdd.toString();
						}
						arg0.addSuggestion(rSAddName,rSAddName.getCompanyName()+street+cityAdd+countryAdd);
						if (arg0.getState().suggestions.size() == 9) {
							break;
						}						
					}
				}
				else {
					List<String> rsult = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getGoogleAutocomplete(arg1);

					for(String s:rsult) {
						RSAddName addName = new RSAddName();
						addName.setCompanyName(s).setNotSaved(true);
						arg0.addSuggestion(addName, s);
					}

				}
			}
		});
		
		return field;
	}
	



	public Label getDeptSignLabel() {
		return deptSignLabel;
	}

	public void setDeptSignLabel(Label deptSignLabel) {
		this.deptSignLabel = deptSignLabel;
	}

	public Label getDestSignLabel() {
		return destSignLabel;
	}

	public void setDestSignLabel(Label destSignLabel) {
		this.destSignLabel = destSignLabel;
	}

	public AutocompleteField<RSAddName> getDeptField() {
		return deptField;
	}

	public void setDeptField(AutocompleteField<RSAddName> deptField) {
		this.deptField = deptField;
	}

	public AutocompleteField<RSAddName> getDestField() {
		return destField;
	}

	public void setDestField(AutocompleteField<RSAddName> destField) {
		this.destField = destField;
	}

	public RSAddName getShipper() {
		return shipper;
	}

	public void setShipper(RSAddName shipper) {
		this.shipper = shipper;
	}

	public RSAddName getConsignee() {
		return consignee;
	}

	public void setConsignee(RSAddName consignee) {
		this.consignee = consignee;
	}

	
}
