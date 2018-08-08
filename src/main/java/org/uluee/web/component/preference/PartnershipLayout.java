package org.uluee.web.component.preference;

import java.util.List;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.PartnershipWrapper;

import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class PartnershipLayout extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5350618571304044566L;
	private TextField amountDepositTf;
	private Button sendButton;
	private Table depositedTable;
	private String sessionId;
	private List<PartnershipWrapper> partnerships;
	private static final String ACTION = "action";
	private static final String AMOUNT = "amount";;
	private static final String PAYMENT = "payment";
	private static final String ACCOUNT_NO = "account_no";

	public PartnershipLayout() {
		this.sessionId = ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId();
		initValues();
		setWidth(100, Unit.PERCENTAGE);
		setMargin(true);
		setSpacing(true);
		createPaymentForm();
	}

	private void initValues() {
		partnerships = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().getPartnership(sessionId);
	}

	private void createPaymentForm() {
		amountDepositTf = new TextField("Amount deposited");
		sendButton = new Button("Send");
		sendButton.addStyleName(ValoTheme.BUTTON_SMALL);
		sendButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		sendButton.addClickListener(e->{
			if(!amountDepositTf.getValue().isEmpty()) {
				try {
					Integer value = Integer.valueOf(amountDepositTf.getValue());
					new WarningLimitWindow(value);				
				}catch(Exception ex) {
					Notification.show("Deposit must be numeric", Type.ERROR_MESSAGE);
				}
			}

		});

		depositedTable = createTable();

		addComponents(amountDepositTf,depositedTable, sendButton);

		amountDepositTf.setWidth(100, Unit.PERCENTAGE);
		depositedTable.setWidth(100, Unit.PERCENTAGE);

	}

	private Table createTable() {
		Table t = new Table();
		t.setHeight(200, Unit.PIXELS);
		t.setWidth(100, Unit.PERCENTAGE);
		t.addContainerProperty(ACCOUNT_NO, String.class, null, "Account no", null, Align.LEFT);
		t.addContainerProperty(PAYMENT, String.class, null, "Payment", null, Align.LEFT);
		t.addContainerProperty(AMOUNT, String.class, null, "Amount", null, Align.LEFT);
		t.addContainerProperty(ACTION, Button.class, null, "Action", null, Align.LEFT);
		if(partnerships != null) {
			for(PartnershipWrapper p : partnerships) {
				Item i = t.addItem(p);
				i.getItemProperty(ACCOUNT_NO).setValue(p.getAccountNo());
				i.getItemProperty(PAYMENT).setValue(p.getPaymentLabel());
				i.getItemProperty(AMOUNT).setValue(p.getAmountDeposit());
				
				Button increaseButton = new Button("Increase amount");
				increaseButton.addStyleName(ValoTheme.BUTTON_SMALL);
				increaseButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
				increaseButton.addClickListener(e->{
					new IncreaseAmountWindow();
				});
			}
		}
		return t;
	}
	
	private class IncreaseAmountWindow extends Window{
		public IncreaseAmountWindow() {
			setModal(true);
			setResizable(false);
			setDraggable(true);
			HorizontalLayout content = createContent();
			setContent(content);
			UI.getCurrent().addWindow(this);
		}

		private HorizontalLayout createContent() {
			HorizontalLayout l = new HorizontalLayout();
			l.setMargin(true);
			TextField newAmount = new TextField("New amount");
			Button okButton = new Button("Change amount");
			okButton.addStyleName(ValoTheme.BUTTON_SMALL);
			okButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
			
			l.addComponent(newAmount);
			l.addComponent(newAmount);
			okButton.addClickListener(e->{
				try {
					if(newAmount.getValue().isEmpty()) {
						Notification.show("Amount is required", Type.ERROR_MESSAGE);
						return;
					}
					String a = newAmount.getValue();
					boolean result = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().increaseDeposit(sessionId, a);
					if(result) {
						UI.getCurrent().removeWindow(IncreaseAmountWindow.this);
						Notification.show("Amount is successfully saved");
					}else {
						Notification.show("Error on saving deposit", Type.ERROR_MESSAGE);
					}
				}catch(Exception ex) {
					Notification.show("Amount must be numeric", Type.ERROR_MESSAGE);
				}
			});
			return l;
		}
	}

	private class WarningLimitWindow extends Window{
		/**
		 * 
		 */
		private static final long serialVersionUID = -3573375648847258605L;
		private Integer amount;

		public WarningLimitWindow(Integer amount) {
			this.amount = amount;
			setModal(true);
			setResizable(false);
			setDraggable(true);
			VerticalLayout content = createContent();
			setContent(content);
			UI.getCurrent().addWindow(this);
		}

		private VerticalLayout createContent() {
			VerticalLayout l = new VerticalLayout();
			l.setMargin(true);
			l.setSpacing(true);
			TextField warningTf = new TextField();
			TextField limitTf = new TextField();
			
			warningTf.setCaption("Warning amount");
			limitTf.setCaption("Limit amount");
			HorizontalLayout buttonLayout = new HorizontalLayout();
			buttonLayout.setSpacing(true);
			Button okButton = new Button("OK");
			Button cancelButton = new Button("Cancel");
			okButton.addStyleName(ValoTheme.BUTTON_SMALL);
			okButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

			cancelButton.addStyleName(ValoTheme.BUTTON_SMALL);
			cancelButton.addStyleName(ValoTheme.BUTTON_DANGER);

			buttonLayout.addComponents(okButton, cancelButton);
			l.addComponents(warningTf, limitTf, buttonLayout);

			cancelButton.addClickListener(e->{
				UI.getCurrent().removeWindow(WarningLimitWindow.this);
			});

			okButton.addClickListener(e->{
				if(warningTf.getValue().isEmpty() || limitTf.getValue().isEmpty()) {
					Notification.show("Please input the value", Type.ERROR_MESSAGE);
				}
				else {
					try {
						Long warningAmoutn = Long.parseLong(warningTf.getValue());
						Long limitAmount = Long.parseLong(limitTf.getValue());
						boolean result = ((Uluee_expressUI)UI.getCurrent()).getWebServiceCaller().sendDeposit(sessionId, amount, String.valueOf(warningAmoutn), String.valueOf(limitAmount));
						if(result) {
							Notification.show("Deposit is successfully saved", Type.HUMANIZED_MESSAGE);
						}else{
							Notification.show("Error on saving deposit", Type.ERROR_MESSAGE);
						}
					}catch(Exception s) {
						Notification.show("Value must be numeric", Type.ERROR_MESSAGE);
					}
				}
			});
			return l;
		}
	}

}

