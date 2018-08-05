package org.uluee.web.booking.tracing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.uluee.web.Uluee_expressUI;

import com.vaadin.data.Item;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;

public class MrnCUCLayout extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4513268968405113580L;
	private String awbNo;
	private String awbStock;
	private String ca3dg;
	private ComboBox customCodeCb;
	private Table numberTable;
	private TextField mrnNoText;
	private VerticalLayout root = new VerticalLayout();
	public MrnCUCLayout(String ca3dg, String awbStock, String awbNo){
		this.ca3dg = ca3dg;
		this.awbStock = awbStock;
		this.awbNo = awbNo;
		root.setSpacing(true);
		root.setMargin(true);
		Table mrnNoTable = createMRNNoTable();
		HorizontalLayout mrnLayout = createMrnLayout();
		ComboBox customCode = createCustomCodeComboBox();
		Button saveMucButton = createSaveMucButton();

		root.addComponent(mrnNoTable);
		root.addComponent(mrnLayout);
		root.addComponent(customCode);
		root.addComponent(saveMucButton);
		UI.getCurrent().addWindow(this);
	}

	private Button createSaveMucButton() {
		Button save = new Button("Save");
		save.addClickListener(e->{
			Collection<?> items = numberTable.getItemIds();
			List itemSelected = new ArrayList<>();
			itemSelected.addAll(items);
			String selected = (String) customCodeCb.getValue();
			String sessionId = ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
			boolean isSuccess = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().saveCucMuc(sessionId, ca3dg, awbStock, awbNo, itemSelected, selected);
			if(isSuccess){
				Notification.show("Save is success", Type.HUMANIZED_MESSAGE);
				MrnCUCLayout.this.close();
			}
		});
		return save;
	}

	private ComboBox createCustomCodeComboBox() {
		String sessionId = ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
		List<String> codes = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getCucCode(sessionId);
		String code = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getCucNumberByAwb(sessionId, ca3dg, awbStock, awbNo);
		customCodeCb = new ComboBox("Custom code");
		customCodeCb.setNullSelectionAllowed(false);

		for(String v: codes){
			customCodeCb.addItem(v);
			customCodeCb.setItemCaption(v, v);
		}
		if(customCodeCb.containsId(code)){
			customCodeCb.select(code);
		}
		return customCodeCb;
	}

	private HorizontalLayout createMrnLayout() {
		HorizontalLayout container = new HorizontalLayout();
		container.setWidth(100, Unit.PERCENTAGE);
		mrnNoText = new TextField("MRN");
		Button addButton = new Button("Delete");
		addButton.addStyleName(ValoTheme.BUTTON_TINY);
		addButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		addButton.addClickListener(e->{
			if(mrnNoText.getValue().isEmpty()){
				Notification.show("Please input MRN number", Type.ERROR_MESSAGE);
				return;
			}
			
			numberTable.addItem(mrnNoText.getValue());
		});
		container.addComponent(mrnNoText);
		container.addComponent(addButton);
		container.setComponentAlignment(mrnNoText, Alignment.MIDDLE_LEFT);
		container.setComponentAlignment(addButton, Alignment.MIDDLE_RIGHT);
		return container;
	}

	private Table createMRNNoTable() {
		String sessionId = ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
		numberTable = new Table();
		numberTable.addContainerProperty(1, String.class, null, "MRN No", null, Align.LEFT);
		numberTable.addContainerProperty(2, String.class, null, "Delete", null, Align.LEFT);
		List<String> result = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getMrnCodeByAwb(sessionId, ca3dg, awbStock, awbNo);
		for(String s: result){
			Item i = numberTable.addItem(s);
			i.getItemProperty(1).setValue(s);
			Button action = new Button("Delete");
			action.addStyleName(ValoTheme.BUTTON_TINY);
			action.addStyleName(ValoTheme.BUTTON_DANGER);
			action.addClickListener(e->{
				numberTable.removeItem(s);
			});
		}
		return numberTable;
	}

}
