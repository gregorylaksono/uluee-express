package org.uluee.web.booking;

import java.util.List;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;
import org.uluee.web.component.MainPage;
import org.uluee.web.component.window.GoogleMapNewDestLayout;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;

public class DeptDestLayout extends VerticalLayout{

	private AutocompleteField deptField;
	private AutocompleteField destField;
	private Label deptSignLabel;
	private Label destSignLabel;
	private RSAddName shipper;
	private RSAddName consignee;

	public DeptDestLayout() {
		createContents();
	}

	private void createContents() {
		setSpacing(true);
		setHeight(70, Unit.PIXELS);
		
		HorizontalLayout deptLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, false);
		HorizontalLayout destLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, false);
		
		deptField = createShipperAutoCompleteComponent();
		destField = createConsigneeAutoCompleteComponent();
		
		deptSignLabel = new Label();
		destSignLabel = new Label();
		
		deptSignLabel.setWidth(null);
		destSignLabel.setWidth(null);
		
		deptLayout.addComponent(deptField);
		deptLayout.addComponent(deptSignLabel);
		
		destLayout.addComponent(destField);
		destLayout.addComponent(destSignLabel);
		
		deptLayout.setExpandRatio(deptField, 1.0f);
		deptLayout.setExpandRatio(deptSignLabel, 0.0f);
		
		destLayout.setExpandRatio(destField, 1.0f);
		destLayout.setExpandRatio(destSignLabel, 0.0f);

		deptField.setWidth(100, Unit.PERCENTAGE);
		destField.setWidth(100, Unit.PERCENTAGE);
		addComponent(deptLayout);
		addComponent(destLayout);
		
	}
	
	private AutocompleteField<RSAddName> createShipperAutoCompleteComponent() {
		AutocompleteField<RSAddName> field = new AutocompleteField<>();
		field.setWidth(100, Unit.PERCENTAGE);
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
		field.setWidth(100, Unit.PERCENTAGE);
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
