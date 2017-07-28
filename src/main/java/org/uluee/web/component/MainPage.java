package org.uluee.web.component;

import java.util.List;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.booking.TracingTab;
import org.uluee.web.cloud.IModalWindowBridge;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;
import org.uluee.web.component.window.DisclaimerLayout;
import org.uluee.web.component.window.GoogleMapNewDestLayout;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
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
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
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
	private OptionGroup bookingOption;
	private DateField fromDate ;
	private DateField toDate;
	private AutocompleteField destField;
	private AutocompleteField deptField;
	private AutocompleteField itemComodityField;
	private ComboBox itemWeightComboBox;
	private ComboBox itemPieceComboBox;
	private ComboBox itemLongComboBox;
	private ComboBox itemWidthComboBox;
	private ComboBox itemHeightComboBox;
	private Label deptSignLabel;
	private Label destSignLabel;
	private RSAddName shipper;
	private RSAddName consignee;

	@Override
	public void enter(ViewChangeEvent event) {

		createContents();
		setSpacing(true);
		setMargin(true);
		setSizeFull();
		initFieldValue();
	}

	private void initFieldValue() {


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
			Panel p = new Panel();
			p.setContent(new DisclaimerLayout());
			p.setHeight(500, Unit.PIXELS);
			Window w = new Window();
			w.setContent(p);
			w.setClosable(true);
			w.setModal(true);
			w.setDraggable(false);
			w.setResizable(false);
			//			w.setSizeUndefined();
			w.center();
			UI.getCurrent().addWindow(w);
		});

		parent.addComponent(submitButton);
		parent.setComponentAlignment(submitButton, Alignment.MIDDLE_LEFT);
		return parent;
	}

	private HorizontalLayout createItemDescriptionLayout() {
		HorizontalLayout parent  = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, new MarginInfo(true, false, false, false), true);
		parent.setHeight(null);

		VerticalLayout leftLayout = (VerticalLayout) UIFactory.createLayout(LayoutType.VERTICAL, SizeType.FULL, null, true);
		VerticalLayout rightLayout = (VerticalLayout) UIFactory.createLayout(LayoutType.VERTICAL, SizeType.FULL, null, true);

		parent.addComponent(leftLayout);
		parent.addComponent(rightLayout);


		//Left layout
		itemPieceComboBox = new ComboBox();
		itemWeightComboBox = new ComboBox();
		itemComodityField = createCommodityAutoCompleteComponent();

		itemPieceComboBox.setInputPrompt("Piece");
		itemWeightComboBox.setInputPrompt("Weight");


		itemPieceComboBox.setWidth(100, Unit.PERCENTAGE);
		itemWeightComboBox.setWidth(100, Unit.PERCENTAGE);
		itemComodityField.setWidth(100, Unit.PERCENTAGE);

		HorizontalLayout topLeftLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, true);
		HorizontalLayout bottomLeftLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, true);
		topLeftLayout.setHeight(null);
		bottomLeftLayout.setHeight(100, Unit.PERCENTAGE);

		leftLayout.addComponent(topLeftLayout);
		leftLayout.addComponent(bottomLeftLayout);

		leftLayout.setExpandRatio(topLeftLayout, 0.0f);
		leftLayout.setExpandRatio(bottomLeftLayout, 1.0f);

		topLeftLayout.addComponent(itemPieceComboBox);
		topLeftLayout.addComponent(itemWeightComboBox);
		topLeftLayout.setExpandRatio(itemPieceComboBox, 0.50f);
		topLeftLayout.setExpandRatio(itemWeightComboBox, 0.50f);
		bottomLeftLayout.addComponent(itemComodityField);

		//Right Layout
		HorizontalLayout topRightLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, true);
		HorizontalLayout bottomRightLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, true);
		topRightLayout.setHeight(null);

		rightLayout.addComponent(topRightLayout);
		rightLayout.addComponent(bottomRightLayout);

		rightLayout.setExpandRatio(topRightLayout, 0.0f);
		rightLayout.setExpandRatio(bottomRightLayout, 1.0f);

		itemLongComboBox = new ComboBox();
		itemWidthComboBox = new ComboBox();
		itemHeightComboBox = new ComboBox();

		itemLongComboBox.setInputPrompt("L (cm)");
		itemWidthComboBox.setInputPrompt("W (cm)");
		itemHeightComboBox.setInputPrompt("H (cm)");

		itemLongComboBox.setWidth(100, Unit.PERCENTAGE);
		itemHeightComboBox.setWidth(100, Unit.PERCENTAGE);
		itemWidthComboBox.setWidth(100, Unit.PERCENTAGE);

		topRightLayout.addComponent(itemLongComboBox);
		topRightLayout.addComponent(itemWidthComboBox);
		topRightLayout.addComponent(itemHeightComboBox);

		topRightLayout.setExpandRatio(itemLongComboBox, 0.33f);
		topRightLayout.setExpandRatio(itemWidthComboBox, 0.33f);
		topRightLayout.setExpandRatio(itemHeightComboBox, 0.33f);

		Button addGoodsButton = new Button("Add Goods");
		bottomRightLayout.addComponent(addGoodsButton);
		addGoodsButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		addGoodsButton.addStyleName(ValoTheme.BUTTON_SMALL);


		return parent;
	}

	private AutocompleteField createCommodityAutoCompleteComponent() {
		AutocompleteField<String> field = new AutocompleteField<>();
		field.setWidth(100, Unit.PERCENTAGE);
		field.setQueryListener(new AutocompleteQueryListener<String>() {

			@Override
			public void handleUserQuery(AutocompleteField<String> arg0, String arg1) {
				User user = ((Uluee_expressUI)UI.getCurrent()).getUser();
				List<Commodity> dbData = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getommodity(arg1, user.getSessionId());

				for (Commodity commodity : dbData) {

					arg0.addSuggestion(String.valueOf(commodity.getCommId()),commodity.getCommName());
					if (arg0.getState().suggestions.size() == 9) {
						break;
					}						
				}


			}
		});
		return field;
	}

	private VerticalLayout createDeptDestLayout() {
		VerticalLayout parent = (VerticalLayout) UIFactory.createLayout(LayoutType.VERTICAL, SizeType.FULL, null, true);
		parent.setHeight(70, Unit.PIXELS);
		
		HorizontalLayout deptLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, false);
		HorizontalLayout destLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, false);
		
		deptField = createShipperAutoCompleteComponent();
		destField = createConsigneeAutoCompleteComponent();
		
		deptSignLabel = new Label();
		destSignLabel = new Label();
		
		deptSignLabel.addStyleName("warning-sign");
		destSignLabel.addStyleName("warning-sign");
		
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
		parent.addComponent(deptLayout);
		parent.addComponent(destLayout);
		return parent;
	}

	private AutocompleteField createShipperAutoCompleteComponent() {
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
						addName.setCompanyName(s);
						arg0.addSuggestion(addName, s);
					}

				}
			}
		});
		
		field.setSuggestionPickedListener(e ->{
			if(e.isNotSaved()) {
				deptSignLabel.addStyleName("warning-sign");
				UIFactory.addWindow(new GoogleMapNewDestLayout(GoogleMapNewDestLayout.SHIPPER, MainPage.this), false, true, true);
			}else {
				deptSignLabel.addStyleName("check-sign");
				shipper = e;
			}
		});
		return field;
	}

	private AutocompleteField createConsigneeAutoCompleteComponent() {
		AutocompleteField<String> field = new AutocompleteField<>();
		field.setWidth(100, Unit.PERCENTAGE);
		field.setQueryListener(new AutocompleteQueryListener<String>() {

			@Override
			public void handleUserQuery(AutocompleteField<String> arg0, String arg1) {
				User user = ((Uluee_expressUI)UI.getCurrent()).getUser();
				List<RSAddName> dbData = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getConsigneeAddressByMatch(arg1, user.getSessionId());
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
						arg0.addSuggestion(rSAddName.getCompanyID(),rSAddName.getCompanyName()+street+cityAdd+countryAdd);
						if (arg0.getState().suggestions.size() == 9) {
							break;
						}						
					}
				}
				else {
					List<String> rsult = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getGoogleAutocomplete(arg1);

					for(String s:rsult) {
						arg0.addSuggestion(s, s);
					}

				}
			}
		});
		return field;
	}
	private HorizontalLayout createDateLayout() {
		HorizontalLayout parent = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, new MarginInfo(true, false, true, false), true);
		parent.setHeight(null);
		fromDate = new DateField();
		toDate = new DateField();

		fromDate.setWidth(100, Unit.PERCENTAGE);
		toDate.setWidth(100, Unit.PERCENTAGE);

		parent.addComponent(fromDate);
		parent.addComponent(toDate);
		return parent;
	}

	private TabSheet createTab() {
		HorizontalLayout topLayout = createCheckBoxAndBasketButtonLayout();
		HorizontalLayout dateLayout = createDateLayout();
		VerticalLayout deptDestLayout = createDeptDestLayout();
		HorizontalLayout itemDescriptionLayout = createItemDescriptionLayout();
		VerticalLayout buttonSubmitLayout = createButtonSubmitLayout();

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

	private HorizontalLayout createCheckBoxAndBasketButtonLayout() {
		HorizontalLayout parent = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.UNDEFINED, null, true);
		bookingOption = new OptionGroup();
		bookingOption.addItem("Deprature");
		bookingOption.addItem("Arrival");

		Button basketButton = new Button("Basket");
		basketButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		basketButton.addStyleName(ValoTheme.BUTTON_SMALL);

		parent.setWidth(100, Unit.PERCENTAGE);
		parent.setHeight(40, Unit.PIXELS);
		parent.addComponent(bookingOption);
		parent.addComponent(basketButton);

		parent.setExpandRatio(bookingOption, 1.0f);
		parent.setExpandRatio(basketButton, 0.0f);

		parent.setComponentAlignment(bookingOption, Alignment.MIDDLE_LEFT);
		parent.setComponentAlignment(basketButton, Alignment.MIDDLE_RIGHT);

		return parent;
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

	@Override
	public void react() {
		
		
	}


}
