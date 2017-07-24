package org.uluee.web.component.window;

import java.util.Collection;
import java.util.Collections;

import org.uluee.web.Uluee_expressUI;
import org.uluee.web.util.NavigatorConstant;

import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class DisclaimerLayout extends CustomLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean initValue = false;
	public DisclaimerLayout() {
		super("Disclaimer");
		addStyleName("auto-height");
		createContents();
	}

	private void createContents() {
		CheckBox agreeCheckBox = new CheckBox("");
		agreeCheckBox.setImmediate(true);
		final Button submitButton = new Button("Submit");
		submitButton.addStyleName(ValoTheme.BUTTON_SMALL);
		submitButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
		addComponent(agreeCheckBox, "checkBox");
		addComponent(submitButton, "submitButton");
		agreeCheckBox.addValueChangeListener(e ->{
			Boolean isChecked = (Boolean) e.getProperty().getValue();
			submitButton.setEnabled(isChecked);
		});
		agreeCheckBox.setValue(initValue);
		submitButton.setEnabled(initValue);
		submitButton.addClickListener(e ->{
			((Uluee_expressUI)UI.getCurrent()).getNavigator().navigateTo(NavigatorConstant.BOOKING_PAGE);
			Collection<Window> windows = UI.getCurrent().getWindows();
			for(Window w: windows) {
				UI.getCurrent().removeWindow(w);
			}
			
		});
	}

}
