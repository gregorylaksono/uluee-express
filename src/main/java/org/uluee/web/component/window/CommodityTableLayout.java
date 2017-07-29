package org.uluee.web.component.window;

import java.util.List;

import org.uluee.web.cloud.IModalWindowBridge;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.CommodityWrapper;
import org.uluee.web.util.UIFactory;
import org.uluee.web.util.UIFactory.LayoutType;
import org.uluee.web.util.UIFactory.SizeType;

import com.vaadin.data.Item;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class CommodityTableLayout extends VerticalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5010830737125921361L;
	private static final String COMMODITY = "com";
	private static final String SCC = "scc";
	private static final String PIECES = "pieces";
	private static final String WEIGHT = "we";
	private static final String LENGTH = "l";
	private static final String WIDTH = "wi";
	private static final String HEIGHT = "h";
	private static final String VOLUME = "v";
	private static final String DELETE = "del";
	private IModalWindowBridge windowParent;
	private List<CommodityWrapper> commodities;
	
	public CommodityTableLayout(IModalWindowBridge parent, List<CommodityWrapper> commodities) {
		setWindowParent(parent);
		setCommodities(commodities);
		setMargin(true);
		setSpacing(true);
		createContents();
		setWidth(680, Unit.PIXELS);
		setHeight(350, Unit.PIXELS);
	}

	private void createContents() {
	Table commodityTable = new Table();
	commodityTable.setHeight(100, Unit.PERCENTAGE);
	commodityTable.setWidth(100, Unit.PERCENTAGE);
	commodityTable.addStyleName("uluee-table");
	commodityTable.addContainerProperty(COMMODITY, String.class, null);
	commodityTable.addContainerProperty(SCC, String.class, null);
	commodityTable.addContainerProperty(PIECES, String.class, null);
	commodityTable.addContainerProperty(WEIGHT, String.class, null);
	commodityTable.addContainerProperty(LENGTH, String.class, null);
	commodityTable.addContainerProperty(WIDTH, String.class, null);
	commodityTable.addContainerProperty(HEIGHT, String.class, null);
	commodityTable.addContainerProperty(VOLUME, String.class, null);
	commodityTable.addContainerProperty(DELETE, Button.class, null);
		
	commodityTable.setColumnHeader(COMMODITY, "Commodity");
	commodityTable.setColumnHeader(SCC, "SCC");
	commodityTable.setColumnHeader(PIECES, "Pieces");
	commodityTable.setColumnHeader(WEIGHT, "Weight");
	commodityTable.setColumnHeader(LENGTH, "Length");
	commodityTable.setColumnHeader(WIDTH, "Width");
	commodityTable.setColumnHeader(HEIGHT, "Height");
	commodityTable.setColumnHeader(VOLUME, "Volume");
	commodityTable.setColumnHeader(DELETE, "Delete");
	
	for(CommodityWrapper c: commodities){
		Item commodity = commodityTable.addItem(c);
		Button deleteButton = new Button("Delete");
		deleteButton.setIcon(FontAwesome.REMOVE);
		deleteButton.setStyleName(ValoTheme.BUTTON_SMALL);
		deleteButton.addStyleName(ValoTheme.BUTTON_DANGER);
		deleteButton.addClickListener( e->{
			commodityTable.removeItem(c);
			commodities.remove(c);
		});
		commodity.getItemProperty(COMMODITY).setValue(c.getCommodity());
		commodity.getItemProperty(SCC).setValue(c.getScc());
		commodity.getItemProperty(PIECES).setValue(String.valueOf(c.getPieces()));
		commodity.getItemProperty(WEIGHT).setValue(String.valueOf(c.getWeight()));
		commodity.getItemProperty(LENGTH).setValue(String.valueOf(c.getLength()));
		commodity.getItemProperty(WIDTH).setValue(String.valueOf(c.getWidth()));
		commodity.getItemProperty(HEIGHT).setValue(String.valueOf(c.getHeight()));
		commodity.getItemProperty(VOLUME).setValue(String.valueOf(c.getVolume()));
		commodity.getItemProperty(DELETE).setValue(deleteButton);
		
		
	}
	HorizontalLayout footer = (HorizontalLayout) UIFactory.createLayout(LayoutType.HORIZONTAL,SizeType.UNDEFINED, null, false);
	footer.setWidth(100, Unit.PERCENTAGE);
	footer.setHeight(50, Unit.PIXELS);
	
	Button closeButton = new Button("Close");
	closeButton.setStyleName(ValoTheme.BUTTON_SMALL);
	closeButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
	closeButton.addClickListener(e ->{
		UIFactory.closeAllWindow();
		windowParent.react(commodities);
	});
	
	footer.addComponent(closeButton);
	footer.setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
	
	addComponent(commodityTable);
	addComponent(footer);
	setComponentAlignment(commodityTable, Alignment.TOP_CENTER);
	setExpandRatio(commodityTable, 1.0f);
	setExpandRatio(footer, 0.0f);
	}

	public IModalWindowBridge getWindowParent() {
		return windowParent;
	}

	public void setWindowParent(IModalWindowBridge windowParent) {
		this.windowParent = windowParent;
	}

	public List<CommodityWrapper> getCommodities() {
		return commodities;
	}

	public void setCommodities(List<CommodityWrapper> commodities) {
		this.commodities = commodities;
	}

}
