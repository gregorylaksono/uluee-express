package org.uluee.web.booking;

import java.util.ArrayList;
import java.util.List;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.CommodityWrapper;
import org.uluee.web.cloud.model.User;
import org.uluee.web.component.MainPage;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;
import org.vaadin.ui.NumberField;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.zybnet.autocomplete.server.AutocompleteField;
import com.zybnet.autocomplete.server.AutocompleteQueryListener;

public class ItemDescriptionLayout extends VerticalLayout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4308359603722700720L;
	private NumberField itemWeightField;
	private NumberField itemPieceField;
	private NumberField itemLongField;
	private NumberField itemWidthField;
	private NumberField itemHeightField;
	private NumberField itemVolumeField;
	private TextField sccField;
	private AutocompleteField itemComodityField;
	private List<CommodityWrapper> commodities = new ArrayList<>();
	private Commodity commodity;
	public ItemDescriptionLayout() {
		createContents();
	}

	private void createContents() {
		setMargin(new MarginInfo(true, false, false, false));
		setSpacing(true);
		setHeight(null);
		setWidth(100, Unit.PERCENTAGE);		


		//Left layout
		sccField = new TextField("SCC");
		itemPieceField = new NumberField("Piece");
		itemWeightField = new NumberField("Weight");
		itemVolumeField = new NumberField("Volume(m3)");
		itemComodityField = createCommodityAutoCompleteComponent();
		itemPieceField.setWidth(100, Unit.PERCENTAGE);
		itemComodityField.setWidth(100, Unit.PERCENTAGE);
		
		itemLongField = new NumberField("Length");
		itemWidthField = new NumberField("Width");
		itemHeightField = new NumberField("Height");

		itemLongField.setInputPrompt("L (cm)");
		itemWidthField.setInputPrompt("W (cm)");
		itemHeightField.setInputPrompt("H (cm)");

		sccField.setWidth(100, Unit.PERCENTAGE);

		itemLongField.setWidth(100, Unit.PIXELS);
		itemHeightField.setWidth(100, Unit.PIXELS);
		itemWidthField.setWidth(100, Unit.PIXELS);
		itemWeightField.setWidth(100, Unit.PIXELS);
		itemVolumeField.setWidth(100, Unit.PIXELS);
		
		Button addGoodsButton = new Button("Add Goods");
		HorizontalLayout topLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, true);
		HorizontalLayout bottomLayout = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL, SizeType.FULL, null, true);
		topLayout.setHeight(null);
		bottomLayout.setHeight(100, Unit.PERCENTAGE);

		addComponent(topLayout);
		addComponent(bottomLayout);

		topLayout.addComponent(itemComodityField);
		topLayout.addComponent(sccField);
		topLayout.addComponent(itemPieceField);
		
		bottomLayout.addComponent(itemWeightField);
		bottomLayout.addComponent(itemLongField);
		bottomLayout.addComponent(itemWidthField);
		bottomLayout.addComponent(itemHeightField);
		bottomLayout.addComponent(itemVolumeField);
		bottomLayout.addComponent(addGoodsButton);
		bottomLayout.setComponentAlignment(addGoodsButton, Alignment.BOTTOM_RIGHT);
		
		
		formatAllNumberFields();

		addGoodsButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		addGoodsButton.addStyleName(ValoTheme.BUTTON_SMALL);
		addGoodsButton.addClickListener(e ->{
			boolean isValid = validateItems(itemLongField,itemWidthField,itemHeightField,itemPieceField,itemWeightField);
			if(isValid){
				Double volume = Double.parseDouble(itemLongField.getValue()) * Double.parseDouble(itemHeightField.getValue()) *
							    Double.parseDouble(itemWidthField.getValue());
				itemVolumeField.setValue(volume);
				CommodityWrapper commodity = new CommodityWrapper();
				
				commodity.setCom_id(String.valueOf(this.commodity.getCommId())).
				setAnn_id(this.commodity.getAnnId()).
				setCommodity(this.commodity.getCommName()).
				setHeight(new Double(itemHeightField.getValue()).intValue()).
				setLength(new Double(itemLongField.getValue()).intValue()).
				setPieces(new Double(itemPieceField.getValue()).intValue()).
				setScc(this.commodity.getSccCode()).
				setVolume(volume).
				setWeight(Double.parseDouble(itemWeightField.getValue())).
				setWidth(new Double(itemWidthField.getValue()).intValue());
				commodities.add(commodity);
				resetAllFields();
			}
		});
		

		resetAllFields();
		
	}
	
	private void formatAllNumberFields() {
		itemPieceField.setDecimalAllowed(false);
		itemPieceField.setDecimalPrecision(0);
		itemPieceField.setGroupingSeparator('.');
		itemPieceField.setMinValue(1d);
		itemPieceField.setMaxLength(3);
		
		itemLongField.setDecimalAllowed(true);
		itemLongField.setDecimalPrecision(2);
		itemLongField.setDecimalSeparator(',');
		itemLongField.setGroupingSeparator('.');
		itemLongField.setMinimumFractionDigits(2);
		itemLongField.setMinValue(1d);
		itemLongField.setMaxLength(3);
		
		itemWidthField.setDecimalAllowed(true);
		itemWidthField.setDecimalPrecision(2);
		itemWidthField.setDecimalSeparator(',');
		itemWidthField.setGroupingSeparator('.');
		itemWidthField.setMinimumFractionDigits(2);
		itemWidthField.setMinValue(1d);
		itemWidthField.setMaxLength(3);
		
		itemHeightField.setDecimalAllowed(true);
		itemHeightField.setDecimalPrecision(2);
		itemHeightField.setDecimalSeparator(',');
		itemHeightField.setGroupingSeparator('.');
		itemHeightField.setMinimumFractionDigits(2);
		itemHeightField.setMinValue(1d);
		itemHeightField.setMaxLength(3);
		
	}
	private void resetAllFields() {
		itemPieceField.setValue(1d);
		itemWeightField.setValue(1d);
		itemLongField.setValue(1d);
		itemWidthField.setValue(1d);
		itemHeightField.setValue(1d);
		itemComodityField.setText("");
		
	}
	

	private boolean validateItems(NumberField itemLongField, NumberField itemWidthField, NumberField itemHeightField,
			NumberField itemPieceField, NumberField itemWeightComboBox) {
		if(itemLongField.getValue() == null || Double.parseDouble(itemLongField.getValue()) < 1) return false;
		if(itemWidthField.getValue() == null || Double.parseDouble(itemWidthField.getValue()) < 1) return false;
		if(itemHeightField.getValue() == null || Double.parseDouble(itemHeightField.getValue()) < 1) return false;
		if(itemPieceField.getValue() == null || Double.parseDouble(itemPieceField.getValue()) < 1) return false;
		if(itemWeightComboBox.getValue() == null || Double.parseDouble(itemWeightComboBox.getValue()) < 1) return false;
		if(commodity == null) return false;
		
		return true;
	}
	

	
	private AutocompleteField createCommodityAutoCompleteComponent() {
		AutocompleteField<Commodity> field = new AutocompleteField<Commodity>();
		field.setCaption("Commodity");
		field.setWidth(100, Unit.PERCENTAGE);
		field.setQueryListener(new AutocompleteQueryListener<Commodity>() {

			@Override
			public void handleUserQuery(AutocompleteField<Commodity> arg0, String arg1) {
				User user = ((Uluee_expressUI)UI.getCurrent()).getUser();
				List<Commodity> dbData = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getCommodity(arg1, user.getSessionId());

				for (Commodity commodity : dbData) {

					arg0.addSuggestion(commodity,commodity.getCommName());
					if (arg0.getState().suggestions.size() == 9) {
						break;
					}						
				}


			}
		});
		field.setSuggestionPickedListener(e->{
			this.commodity = e;
		});
		return field;
	}

	public NumberField getItemWeightField() {
		return itemWeightField;
	}

	public void setItemWeightField(NumberField itemWeightField) {
		this.itemWeightField = itemWeightField;
	}

	public NumberField getItemPieceField() {
		return itemPieceField;
	}

	public void setItemPieceField(NumberField itemPieceField) {
		this.itemPieceField = itemPieceField;
	}

	public NumberField getItemLongField() {
		return itemLongField;
	}

	public void setItemLongField(NumberField itemLongField) {
		this.itemLongField = itemLongField;
	}

	public NumberField getItemWidthField() {
		return itemWidthField;
	}

	public void setItemWidthField(NumberField itemWidthField) {
		this.itemWidthField = itemWidthField;
	}

	public NumberField getItemHeightField() {
		return itemHeightField;
	}

	public void setItemHeightField(NumberField itemHeightField) {
		this.itemHeightField = itemHeightField;
	}

	public AutocompleteField getItemComodityField() {
		return itemComodityField;
	}

	public void setItemComodityField(AutocompleteField itemComodityField) {
		this.itemComodityField = itemComodityField;
	}

	public List<CommodityWrapper> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<CommodityWrapper> commodities) {
		this.commodities = commodities;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public NumberField getItemVolumeField() {
		return itemVolumeField;
	}

	public void setItemVolumeField(NumberField itemVolumeField) {
		this.itemVolumeField = itemVolumeField;
	}

	public TextField getSccField() {
		return sccField;
	}

	public void setSccField(TextField sccField) {
		this.sccField = sccField;
	}
	
	
}
